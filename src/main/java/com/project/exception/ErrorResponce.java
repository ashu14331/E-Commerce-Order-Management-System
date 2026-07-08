package com.project.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorResponce {
    private int statuscode;
    private String message;
    private LocalDateTime timestamp;

}
