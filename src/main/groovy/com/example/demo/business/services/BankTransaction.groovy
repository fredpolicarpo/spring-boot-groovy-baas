package com.example.demo.business.services

import com.example.demo.business.entities.Account
import com.example.demo.business.entities.OperationType
import com.example.demo.business.entities.Transaction
import com.example.demo.business.ports.Timer
import groovy.transform.Canonical
import org.springframework.stereotype.Service

@Canonical
@Service
class BankTransaction {
    final Timer timer

    Transaction execute(final OperationType operationType, final BigDecimal amount, final Account account) {
        BigDecimal transactionAmount = convertAmountForOperationType(operationType, amount)

        return new Transaction(
                amount: transactionAmount,
                operationType: operationType,
                account: account,
                eventDate: timer.now()
        )
    }

    private static BigDecimal convertAmountForOperationType(OperationType operationType, BigDecimal amount) {
        return OperationType.DEPOSIT == operationType ? amount : amount * -1
    }
}
