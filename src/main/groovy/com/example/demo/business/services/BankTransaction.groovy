package com.example.demo.business.services

import com.example.demo.business.entities.Account
import com.example.demo.business.entities.OperationType
import com.example.demo.business.entities.Transaction

class BankTransaction {
    Transaction execute(final OperationType operationType, final BigDecimal amount, final Account account) {
        return new Transaction(amount: -amount)
    }
}
