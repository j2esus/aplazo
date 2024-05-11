package com.aplazo.scheme.exceptions;

import lombok.Data;

@Data
public class CustomErrorResponse {
    private String status;
    private String error;
}
