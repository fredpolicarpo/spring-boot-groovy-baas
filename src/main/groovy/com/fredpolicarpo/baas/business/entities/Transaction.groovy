package com.fredpolicarpo.baas.business.entities

import java.time.Instant


class Transaction {
    Long id
    Account account
    OperationType operationType
    Instant eventDate
    BigDecimal amount
}