package org.example.services;

import org.example.dto.WorkerDTO;

import java.util.Map;

public interface WorkerService {

    WorkerDTO hireWorker(Map<String, String> params);

    WorkerDTO fireWorker(String workerId);
}
