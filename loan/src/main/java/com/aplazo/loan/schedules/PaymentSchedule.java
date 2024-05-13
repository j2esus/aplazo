package com.aplazo.loan.schedules;

import com.aplazo.loan.services.LoanService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@EnableAsync
@Slf4j
public class PaymentSchedule {

    private final LoanService loanService;

    @Async
    @Scheduled(cron = "0 0 2 * * *")
    public void processPaymentsAsync() {
        log.info("Automatic process for payments");
        loanService.processPayments();
    }
}
