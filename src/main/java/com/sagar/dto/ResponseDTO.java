package com.sagar.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDTO <T> {
    private String msg;
    private int statusCode;
    private T data;
}
