package com.fredpolicarpo.baas.business.entities

import java.time.Instant


class Transaction {
    Long id
    Account account
    OperationType operationType
    BigDecimal amount
    BigDecimal balance
    Instant eventDate

    void adjustBalance(BigDecimal money) {
        balance -= money
    }
}