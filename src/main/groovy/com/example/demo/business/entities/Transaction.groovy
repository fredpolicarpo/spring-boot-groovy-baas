package com.example.demo.business.entities

import java.time.Instant


class Transaction {
    Account account
    OperationType operationType
    Instant eventDate
    BigDecimal amount
}