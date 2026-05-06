package com.sagar.exceptions;

import com.sagar.dto.ResponseDTO;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Throwable> {

    private static final Logger LOG = Logger.getLogger(GlobalExceptionHandler.class);

    @Override
    public Response toResponse(Throwable exception) {
        if (exception instanceof ApplicationException) {
            ApplicationException appEx = (ApplicationException) exception;
            LOG.warnf("Application error [%d]: %s", appEx.statusCode, exception.getMessage());
            return Response.status(appEx.statusCode).entity(appEx.responseDTO).build();
        }
        LOG.errorf(exception, "Unexpected error: %s", exception.getMessage());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(ResponseDTO.builder()
                        .msg("An internal server error occurred. Please try again later.")
                        .statusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                        .build())
                .build();
    }
}


