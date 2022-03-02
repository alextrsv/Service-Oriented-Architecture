package soa.labs.controllers;

import lombok.SneakyThrows;
import soa.labs.dto.WorkerDTO;
import soa.labs.exception.InvalidStartDateException;
import soa.labs.services.WorkerService;
import soa.labs.util.RemoteBeanLookupUtil;
import soa.labs.util.Validation;

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

//    private final WorkerService remoteService;

    @SneakyThrows
    public WorkerResource() {
        this.validation = new Validation();
//        this.workerService = new WorkerServiceImpl();
        this.workerService = RemoteBeanLookupUtil.lookupBarsBean();
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


        if (!startDate.matches("((?:19|20)[0-9][0-9])-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])"))
            throw new InvalidStartDateException();
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


    @GET
    @Path("remote/multiply")
    @Produces(MediaType.TEXT_PLAIN)
    public int calculateRemote() {
        return workerService.calculate(1, 4);
    }

}
