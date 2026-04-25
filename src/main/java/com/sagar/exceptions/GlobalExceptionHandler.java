package com.sagar.exceptions;

import com.sagar.dto.ResponseDTO;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Throwable> {
    @Override
    public Response toResponse(Throwable exception) {
        if (exception instanceof ApplicationException) {
            ApplicationException appEx = (ApplicationException) exception;
            return Response.status(appEx.statusCode).entity(appEx.responseDTO).build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(ResponseDTO.builder()
                        .msg("An unexpected error occurred: " + exception.getMessage())
                        .statusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                        .build())
                .build();

    }
}
