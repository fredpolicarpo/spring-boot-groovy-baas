package com.fredpolicarpo.baas.business.services

import com.fredpolicarpo.baas.business.entities.Account
import com.fredpolicarpo.baas.business.entities.OperationType
import com.fredpolicarpo.baas.business.entities.Transaction
import com.fredpolicarpo.baas.business.exceptions.InsuficientLimitException
import com.fredpolicarpo.baas.business.ports.Timer
import groovy.transform.Canonical

@Canonical
class BankTransaction {
    final Timer timer
    Transaction execute(final OperationType operationType, final BigDecimal amount, final Account account) {
        BigDecimal transactionAmount = convertAmountForOperationType(operationType, amount)

        if (account.creditLimit + transactionAmount < 0) {
            throw new InsuficientLimitException(creditLimit: account.creditLimit, transactionAmount: transactionAmount)
        }

        account.creditLimit += transactionAmount

        return new Transaction(
                amount: transactionAmount,
                operationType: operationType,
                account: account,
                balance: transactionAmount,
                eventDate: timer.now()
        )
    }

    private static BigDecimal convertAmountForOperationType(OperationType operationType, BigDecimal amount) {
        return OperationType.DEPOSIT == operationType ? amount : amount * -1
    }
}
