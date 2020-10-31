package com.fredpolicarpo.baas.business.entities

enum OperationType {
    CASH_PAYMENT(1),
    INSTALLMENT_PAYMENT(2),
    WITHDRAW(3),
    DEPOSIT(4),

    int code

    OperationType(final int code) {
        this.code = code
    }
}