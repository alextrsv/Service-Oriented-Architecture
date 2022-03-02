package soa.labs.repositories;

import soa.labs.entities.Worker;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkerRepository extends CrudRepository<Worker, Integer>, WorkerRepositoryCustom {

    @Override
    List<Worker> findAll();

    @Query("SELECT w FROM Worker w")
    List<Worker> findAllWithPagination(PageRequest page);
}
