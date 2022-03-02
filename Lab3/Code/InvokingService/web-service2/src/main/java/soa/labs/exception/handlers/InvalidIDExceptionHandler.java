package soa.labs.exception.handlers;

import soa.labs.exception.InvalidIDException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidIDExceptionHandler implements ExceptionMapper<InvalidIDException> {

    @Override
    public Response toResponse(InvalidIDException e) {
        return Response
                .status(Response.Status.NOT_FOUND)
                .entity(e.getMessage())
                .build();
    }
}
