package soa.labs.services;


import soa.labs.dto.WorkerDTO;

import java.util.Map;

public interface WorkerService {

    int calculate(int a, int b);

    WorkerDTO hireWorker(Map<String, String> params);

    WorkerDTO fireWorker(String workerId);
}
