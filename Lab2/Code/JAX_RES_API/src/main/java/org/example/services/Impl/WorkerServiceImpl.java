package org.example.services.Impl;

import lombok.SneakyThrows;
import org.example.dto.WorkerDTO;
import org.example.exception.BaseException;
import org.example.exception.NoSuchWorkerException;
import org.example.services.WorkerService;
import org.example.util.ClientFactoryBuilder;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.util.Map;

public class WorkerServiceImpl implements WorkerService {

    private final Client client;

    public WorkerServiceImpl() {
        this.client = ClientFactoryBuilder.getClient();
    }

    @SneakyThrows
    @Override
    public WorkerDTO hireWorker(Map<String, String> params) {

        WorkerDTO workerDTO = new WorkerDTO();

        Response response = client.target(ClientFactoryBuilder.getBaseUrl())
                .queryParam("id", params.get("id"))
                .queryParam("organizationId", params.get("organizationId"))
                .queryParam("position", params.get("position").toUpperCase())
                .queryParam("startDate", params.get("startDate"))
                .queryParam("status", "HIRED")
                .request()
                .put(Entity.json(workerDTO));

        if (response.getStatus() == 404){
            throw new NoSuchWorkerException(params.get("id"));
        }
        else if (response.getStatus() > 300)
            throw new BaseException();
        else {
            workerDTO = response.readEntity(WorkerDTO.class);
            return workerDTO;
        }
    }

    @SneakyThrows
    @Override
    public WorkerDTO fireWorker(String workerId) {
        WorkerDTO workerDTO = new WorkerDTO();
        Response response = client.target(ClientFactoryBuilder.getBaseUrl())
                .queryParam("id", workerId)
                .queryParam("status", "FIRED")
                .request()
                .put(Entity.json(workerDTO));

        if (response.getStatus() == 404){
            throw new NoSuchWorkerException(workerId);
        }
        else if (response.getStatus() > 300)
            throw new BaseException();
        else {
            workerDTO = response.readEntity(WorkerDTO.class);
            return workerDTO;
        }
    }
}
