package soa.labs;

import io.spring.guides.gs_producing_web_service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import soa.labs.entities.dto.NewWorkerReqParam;
import soa.labs.entities.dto.UPDWorkerParam;
import soa.labs.entities.dto.WorkerDTO;
import soa.labs.services.WorkerService;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


@Endpoint
public class WorkerEndpoint {
    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

    final WorkerService workerServiceImpl;

    @Autowired
    public WorkerEndpoint(WorkerService workerServiceImpl) {
        this.workerServiceImpl = workerServiceImpl;
    }


    private WorkerSoapDTO mapWorkerToSoapDTO(WorkerDTO workerDTO) {
        WorkerSoapDTO workerSoapDTO = new WorkerSoapDTO();

        workerSoapDTO.setId(workerDTO.getId());
        workerSoapDTO.setName(workerDTO.getName());
        workerSoapDTO.setCreationDate(workerDTO.getCreationDate());
        workerSoapDTO.setSalary(workerDTO.getSalary());
        workerSoapDTO.setStartDate(workerDTO.getStartDate());
        workerSoapDTO.setPosition(workerDTO.getPosition().getPosition());
        workerSoapDTO.setStatus(workerDTO.getStatus().getStatus());

        CoordinatesSoapDTO responseCoordinates = new CoordinatesSoapDTO();
        responseCoordinates.setX(workerDTO.getCoordinates().getX());
        responseCoordinates.setY(workerDTO.getCoordinates().getY());


        OrganizationSoapDTO responseOrganization = new OrganizationSoapDTO();

        responseOrganization.setId(workerDTO.getOrganization().getId());
        responseOrganization.setAnnualTurnover(workerDTO.getOrganization().getAnnualTurnover());
        responseOrganization.setEmployeesCount(workerDTO.getOrganization().getEmployeesCount());

        workerSoapDTO.setCoordinates(responseCoordinates);
        workerSoapDTO.setOrganization(responseOrganization);

        return workerSoapDTO;
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getWorkerRequest")
    @ResponsePayload
    public GetWorkerResponse getWorker(@RequestPayload GetWorkerRequest request) {
        GetWorkerResponse response = new GetWorkerResponse();

        response.setWorker(mapWorkerToSoapDTO(workerServiceImpl.getWorker(request.getId()).get().get(0)));

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "filterWorkersRequest")
    @ResponsePayload
    public FilterWorkersResponse getWorkers(@RequestPayload FilterWorkersRequest request) {
        FilterWorkersResponse response = new FilterWorkersResponse();

        Map<String, String> allRequestParams = new HashMap<>();

        if (request.getSize() != null) allRequestParams.put("size", request.getSize());
        else allRequestParams.put("size", String.valueOf(10));
        if (request.getPage() != null) allRequestParams.put("page", request.getPage());
        else allRequestParams.put("page", String.valueOf(0));
        if (request.getFilter() != null) allRequestParams.put("filter", request.getFilter());
        if (request.getSort() != null) allRequestParams.put("sort", request.getSort());

        workerServiceImpl.getAllWorkers(allRequestParams).ifPresent(workerDTOS ->
                response.getWorkers().addAll(workerDTOS.stream().map(this::mapWorkerToSoapDTO).collect(Collectors.toList())));

        return response;
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addWorkerRequest")
    @ResponsePayload
    public AddWorkerResponse addWorker(@RequestPayload AddWorkerRequest request) {
        AddWorkerResponse response = new AddWorkerResponse();

        NewWorkerReqParam newWorkerParams = new NewWorkerReqParam();
        newWorkerParams.setName(request.getName());
        newWorkerParams.setXCoord(request.getXCoord());
        newWorkerParams.setYCoord(request.getYCoord());
        newWorkerParams.setSalary(request.getSalary());
        newWorkerParams.setStartDate(request.getStartDate());
        newWorkerParams.setPosition(request.getPosition());
        newWorkerParams.setStatus(request.getStatus());
        newWorkerParams.setOrganizationId(request.getOrganizationId());

        response.setWorker(mapWorkerToSoapDTO(workerServiceImpl.addNewWorker(newWorkerParams).get()));

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updWorkerRequest")
    @ResponsePayload
    public UpdWorkerResponse updWorker(@RequestPayload UpdWorkerRequest request) {
        UpdWorkerResponse response = new UpdWorkerResponse();

        UPDWorkerParam updWorkerParam = new UPDWorkerParam();

        updWorkerParam.setId(request.getId());
        updWorkerParam.setName(request.getName());
        updWorkerParam.setXCoord(request.getXCoord());
        updWorkerParam.setYCoord(request.getYCoord());
        updWorkerParam.setSalary(request.getSalary());
        updWorkerParam.setStartDate(request.getStartDate());
        updWorkerParam.setPosition(request.getPosition());
        updWorkerParam.setStatus(request.getStatus());
        updWorkerParam.setOrganizationId(request.getOrganizationId());

        response.setWorker(mapWorkerToSoapDTO(workerServiceImpl.updateWorker(updWorkerParam).get()));

        return response;

    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteWorkerRequest")
    @ResponsePayload
    public DeleteWorkerResponse deleteWorker(@RequestPayload DeleteWorkerRequest request) {
        DeleteWorkerResponse response = new DeleteWorkerResponse();

        response.setStatus(workerServiceImpl.deleteWorker(String.valueOf(request.getId())));

        return response;
    }

}
