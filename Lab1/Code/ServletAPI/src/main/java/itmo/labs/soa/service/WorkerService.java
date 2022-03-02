package itmo.labs.soa.service;

import itmo.labs.soa.entities.Worker;
import itmo.labs.soa.entities.dto.WorkerDTO;

import java.util.List;
import java.util.Map;

public interface WorkerService {

    WorkerDTO addNewWorker(Map<String, String[]> params) throws Exception;

    WorkerDTO addNewWorker(Map<String, String[]> params, Worker worker) throws Exception;

    WorkerDTO getById(String workerId) throws Exception;

    WorkerDTO update(Map<String, String[]> params) throws Exception;

    List<WorkerDTO> getAll();

    void delete(String workerId) throws Exception;

//    List<WorkerDTO> getFilteredList(Map<String, String[]> params);

//    List<WorkerDTO> getSortedList();

    List<WorkerDTO> getModifiedList(Map<String, String[]> parameterMap);

    List<WorkerDTO> getByMaxPosition(Map<String, String[]> parameterMap);
}
