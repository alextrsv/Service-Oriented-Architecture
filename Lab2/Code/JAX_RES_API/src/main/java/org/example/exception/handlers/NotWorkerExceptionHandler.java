package org.example.exception.handlers;

import org.example.exception.NoSuchWorkerException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotWorkerExceptionHandler implements ExceptionMapper<NoSuchWorkerException> {


    @Override
    public Response toResponse(NoSuchWorkerException e) {
        return Response
                .status(Response.Status.NOT_FOUND)
                .entity(e.getMessage())
                .build();
    }
}
