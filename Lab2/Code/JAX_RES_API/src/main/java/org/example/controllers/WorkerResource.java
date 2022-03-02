package org.example.controllers;


import lombok.SneakyThrows;
import org.example.dto.WorkerDTO;
import org.example.services.Impl.WorkerServiceImpl;
import org.example.services.WorkerService;
import org.example.util.Validation;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("worker")
public class WorkerResource {

    private final WorkerService workerService;

    private final Validation validation;

    public WorkerResource() {
        this.validation = new Validation();
        this.workerService = new WorkerServiceImpl();
    }

    @GET
    @Path("hi")
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }

    @SneakyThrows
    @POST
    @Path("hire/{person-id}/{org-id}/{position}/{start-date}")
    @Produces(MediaType.APPLICATION_JSON)
    public WorkerDTO hireWorker(@PathParam("person-id") String workerId,
                                @PathParam("org-id") String organizationId,
                                @PathParam("position") String position,
                                @PathParam("start-date") String startDate) {

        validation.checkId(workerId);
        validation.checkInteger(organizationId, "organizationId");
        validation.handlePosition(position, "position");


        Map<String, String> params = new HashMap<>();
        params.put("id", workerId);
        params.put("organizationId", organizationId);
        params.put("position", position);
        params.put("startDate", startDate);

        return workerService.hireWorker(params);
    }
    @POST
    @Path("fire/{person-id}")
    @Produces(MediaType.APPLICATION_JSON)
    public WorkerDTO fireWorker(@PathParam("person-id")
                             @NotEmpty(message = "id can't be empty!") @NotNull(message = "id can't be empty!") String workerId) {

        validation.checkId(workerId);
        return workerService.fireWorker(workerId);
    }

}
