package soa.labs.exception.handlers;

import soa.labs.exception.NegativeValueException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NegativeValueExceptionHandler implements ExceptionMapper<NegativeValueException> {
    @Override
    public Response toResponse(NegativeValueException e) {
        return Response
                .status(Response.Status.NOT_FOUND)
                .entity(e.getMessage())
                .build();
    }
}
