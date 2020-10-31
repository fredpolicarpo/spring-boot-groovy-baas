package com.fredpolicarpo.baas.business.entities

import java.time.Instant


class Transaction {
    Account account
    OperationType operationType
    Instant eventDate
    BigDecimal amount
}