package soa.labs.exception.handlers;

import soa.labs.exception.InvalidStartDateException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidStartDateHandler implements ExceptionMapper<InvalidStartDateException> {

    @Override
    public Response toResponse(InvalidStartDateException e) {
        return Response
                .status(Response.Status.NOT_FOUND)
                .entity(e.getMessage())
                .build();
    }
}
