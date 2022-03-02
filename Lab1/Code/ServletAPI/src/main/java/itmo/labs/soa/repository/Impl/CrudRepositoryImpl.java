package itmo.labs.soa.repository.Impl;

import itmo.labs.soa.repository.CrudRepository;
import itmo.labs.soa.utils.SessionFactoryBuilder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CrudRepositoryImpl<T> implements CrudRepository<T> {
    private final SessionFactory sessionFactory;
    private final Class<T> tClass;
    private String hQLQueryText = "";

    public CrudRepositoryImpl(Class<T> tClass) {
        this.sessionFactory = SessionFactoryBuilder.getSessionFactory();
        this.tClass = tClass;
    }

    @Override
    public List<T> findByCriteria(CriteriaQuery<T> criteriaQuery) {
        EntityManager em = sessionFactory.createEntityManager();
        return em.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public Optional<T> findById(Integer id) {
        EntityManager em = sessionFactory.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(tClass);
        Root<T> root = query.from(tClass);
        query.select(root);
        query.where(cb.equal(root.get("id"), id));
        return Optional.ofNullable(em.createQuery(query).getSingleResult());
    }

    @Override
    public T update(T entry) {
        EntityManager em = sessionFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(entry);
        em.flush();
        em.getTransaction().commit();
        return entry;
    }

    @Override
    public void save(T entry) {
        Session session = openSession();
        session.getTransaction().begin();
//        session.merge(entry);
        session.saveOrUpdate(entry);
        session.flush();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void deleteById(Integer id) {
        EntityManager em = sessionFactory.createEntityManager();
        em.getTransaction().begin();
        em.remove(this.findById(id).get());
        em.getTransaction().commit();
    }

    @Override
    public List<T> getAll(){
        List<T> resultList;
        Session session = openSession();
        session.getTransaction().begin();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = criteriaBuilder.createQuery(tClass);
        Root<T> root = query.from(tClass);

        CriteriaQuery<T> selectQuery = query.select(root);

        resultList = session.createQuery(selectQuery).getResultList();
        session.getTransaction().commit();
        return resultList;
    }

    @Override
    public List<T> getWithSortAndFilter(List<String> filterParams, List<String> sortParams, int itemsPerPage, int pageNumber){
        List<T> resultList;
        hQLQueryText = "FROM " + tClass.getCanonicalName() + " cl";

        if (filterParams.size() > 0)
            addFilterParams( filterParams);
        if (sortParams!= null) {
            if (sortParams.size() > 0)
                addSortParams(sortParams);
        }
        Session session = openSession();
        session.getTransaction().begin();

        resultList = makeHQLQuery(hQLQueryText, itemsPerPage, pageNumber);

        session.getTransaction().commit();
        return resultList;
    }

    private void addSortParams(List<String> sortParams) {
        hQLQueryText += " ORDER BY ";

        for (int i = 0; i < sortParams.size(); i++){
            hQLQueryText += "cl." +sortParams.get(i) + " ";
            if (i != sortParams.size() - 1) hQLQueryText += ", ";
        }
    }

    private void addFilterParams(List<String> filterParams) {

        hQLQueryText += " WHERE ";
        String AND = "";
        for (int i = 0; i  < filterParams.size(); i++){
            hQLQueryText += AND + " cl." + filterParams.get(i);
            if (i != filterParams.size() - 1) AND = " AND ";
            else AND = "";
        }
    }



    public List<T> makeHQLQuery(String queryTxt, int itemsPerPage, int pageNumber){
        List<T> resultList = new ArrayList<>();
        Session session = openSession();
        session.getTransaction().begin();
        Query query = session.createQuery(queryTxt);
        query.setFirstResult(itemsPerPage * pageNumber);
        query.setMaxResults(itemsPerPage);
        session.getTransaction().commit();
        return query.getResultList();
    }

    @Override
    public EntityManager createEntityManager() {
        return sessionFactory.createEntityManager();
    }

    @Override
    public Session openSession() {
        return sessionFactory.openSession();
    }

}
