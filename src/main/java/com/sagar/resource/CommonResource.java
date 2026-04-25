package com.sagar.resource;

import com.sagar.dto.ResponseDTO;

public class CommonResource {

    public <T> ResponseDTO<T> buildResponse(String msg, int statusCode, T data){
        return ResponseDTO.<T>builder()
                .msg(msg)
                .statusCode(statusCode)
                .data(data)
                .build();
    }
}
