package com.project.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({ "success", "message", "data" })
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;

    public ApiResponse(boolean success,T data) {
        this.success = success;
        this.data = data;
    }

//    public ApiResponse(boolean success, String message, T data) {
//        this.success = success;
//        this.message = message;
//        this.data = data;
//    }
    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}