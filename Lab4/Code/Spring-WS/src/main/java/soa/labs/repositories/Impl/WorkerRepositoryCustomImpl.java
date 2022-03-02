package soa.labs.repositories.Impl;

import soa.labs.entities.Worker;
import soa.labs.repositories.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class WorkerRepositoryCustomImpl implements WorkerRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    String hQLQueryText = "";

    @Override
    public List<Worker> getWithSortAndFilter(List<String> filterParams, List<String> sortParams, int itemsPerPage, int pageNumber) {
        List<Worker> resultList;
        hQLQueryText = "FROM Worker w";

        if (filterParams.size() > 0)
            if (!filterParams.get(0).equals(""))  //MULE FIX
                addFilterParams(filterParams);
        if (sortParams != null) {
            if (sortParams.size() > 0)
                if (!sortParams.get(0).equals("")) //MULE FIX
                    addSortParams(sortParams);
        }

        resultList = makeHQLQuery(hQLQueryText, itemsPerPage, pageNumber);

        return resultList;
    }

    private void addSortParams(List<String> sortParams) {
        hQLQueryText += " ORDER BY ";

        for (int i = 0; i < sortParams.size(); i++) {
            hQLQueryText += "w." + sortParams.get(i) + " ";
            if (i != sortParams.size() - 1) hQLQueryText += ", ";
        }
    }

    private void addFilterParams(List<String> filterParams) {

        hQLQueryText += " WHERE ";
        String AND = "";
        for (int i = 0; i < filterParams.size(); i++) {
            hQLQueryText += AND + " w." + filterParams.get(i);
            if (i != filterParams.size() - 1) AND = " AND ";
            else AND = "";
        }
    }


    public List<Worker> makeHQLQuery(String queryTxt, int itemsPerPage, int pageNumber) {
        return (List<Worker>) entityManager.createQuery(queryTxt).setFirstResult(itemsPerPage * pageNumber).setMaxResults(itemsPerPage).getResultList();
    }
}
