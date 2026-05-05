package com.sagar.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDTO<T> {
    @JsonProperty("msg")
    private String msg;
    @JsonProperty("statusCode")
    private int statusCode;
    @JsonProperty("data")
    private T data;
}
