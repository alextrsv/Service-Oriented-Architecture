package itmo.soa.baseservice.services;

import itmo.soa.baseservice.entities.dto.UPDWorkerParam;
import itmo.soa.baseservice.entities.dto.WorkerDTO;
import itmo.soa.baseservice.entities.dto.NewWorkerReqParam;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface WorkerService {

    Optional<List<WorkerDTO>> getAllWorkers(Map<String,String> params);

    Optional<List<WorkerDTO>> getWorker(int id);

    Optional<WorkerDTO> addNewWorker(NewWorkerReqParam newWorkerReqParam);

    Optional<WorkerDTO> updateWorker(UPDWorkerParam updWorkerParam);

    Optional<String> deleteWorker(String id);
}
