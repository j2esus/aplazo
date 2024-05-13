package com.aplazo.loan.services;

import com.aplazo.loan.entities.Payment;
import com.aplazo.loan.exceptions.ErrorPaymentException;

// This could change for every provider operation to apply the amount: transaction, credit card, etc.
public class ProcessPaymentFacade {

    void processPayment(Payment payment) throws ErrorPaymentException {
        // Apply the logic for the correct operation.
    };
}
