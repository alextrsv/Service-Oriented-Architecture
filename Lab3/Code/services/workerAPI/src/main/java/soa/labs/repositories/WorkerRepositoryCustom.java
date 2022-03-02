package soa.labs.repositories;

import soa.labs.entities.Worker;

import java.util.List;

public interface WorkerRepositoryCustom {
    List<Worker> getWithSortAndFilter(List<String> filterParams, List<String> sortParams, int itemsPerPage, int pageNumber);
}
