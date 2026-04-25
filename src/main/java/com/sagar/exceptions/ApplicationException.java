package com.sagar.exceptions;


import com.sagar.dto.ResponseDTO;

public class ApplicationException extends RuntimeException {
    public int statusCode;
    public ResponseDTO responseDTO;
    public ApplicationException(String msg, int statusCode){
        super(msg);
        responseDTO = ResponseDTO.builder().msg(msg).statusCode(statusCode).build();
        this.statusCode=statusCode;
    }
}
