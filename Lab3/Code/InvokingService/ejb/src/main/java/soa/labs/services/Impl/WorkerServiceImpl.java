package soa.labs.services.Impl;

import lombok.SneakyThrows;
import soa.labs.dto.WorkerDTO;
import soa.labs.exception.BaseException;
import soa.labs.exception.NoSuchWorkerException;
import soa.labs.services.WorkerService;
import soa.labs.util.ClientFactoryBuilder;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.util.Map;

@Stateless
@Remote(WorkerService.class)
public class WorkerServiceImpl implements WorkerService {

    private final Client client;

    public WorkerServiceImpl() {
        this.client = ClientFactoryBuilder.getClient();
    }

    public int calculate(int a, int b) {
        return a + b;
    }

    @SneakyThrows
    @Override
    public WorkerDTO hireWorker(Map<String, String> params) {

        WorkerDTO workerDTO = new WorkerDTO();


        Response response;
//        try {
            response = client.target(ClientFactoryBuilder.getBaseUrl())
                    .queryParam("id", params.get("id"))
                    .queryParam("organizationId", params.get("organizationId"))
                    .queryParam("position", params.get("position").toUpperCase())
                    .queryParam("startDate", params.get("startDate"))
                    .queryParam("status", "HIRED")
                    .request()
                    .put(Entity.json(workerDTO));


            if (response.getStatus() == 404) {
                throw new NoSuchWorkerException(params.get("id"));
//        } else if (response.getStatus() > 300)
//            throw new BaseException();
            } else {
//                try {
                    workerDTO = response.readEntity(WorkerDTO.class);
                    return workerDTO;
//                }catch (Exception exception){
//                    String exDescription = response.readEntity(String.class);
//                    throw new BaseException(exDescription);
//                }
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

        if (response.getStatus() == 404) {
            throw new NoSuchWorkerException(workerId);
        } else if (response.getStatus() > 300)
            throw new BaseException("fire ex");
        else {
            workerDTO = response.readEntity(WorkerDTO.class);
            return workerDTO;
        }
    }

}
