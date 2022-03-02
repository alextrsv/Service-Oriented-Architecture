package soa.labs.exception.handlers;

import soa.labs.exception.BaseException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ExceptionHandler implements ExceptionMapper<BaseException> {


    @Override
    public Response toResponse(BaseException e) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .build();
    }
}
