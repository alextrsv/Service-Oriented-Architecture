package soa.labs.services;

import soa.labs.dto.WorkerDTO;

import javax.ejb.Remote;
import java.util.Map;

@Remote
public interface WorkerService {

    int calculate(int a, int b);

    WorkerDTO hireWorker(Map<String, String> params);

    WorkerDTO fireWorker(String workerId);
}
