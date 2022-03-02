package org.example.exception.handlers;

import org.example.exception.NoSuchPositionException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NoSuchPositionExceptionHandler implements ExceptionMapper<NoSuchPositionException> {
    @Override
    public Response toResponse(NoSuchPositionException e) {
        return Response
                .status(Response.Status.NOT_FOUND)
                .entity(e.getMessage())
                .build();
    }
}
