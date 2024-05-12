package com.aplazo.loan.exceptions;

import lombok.Data;

@Data
public class CustomErrorResponse {
    private String status;
    private String error;
}
