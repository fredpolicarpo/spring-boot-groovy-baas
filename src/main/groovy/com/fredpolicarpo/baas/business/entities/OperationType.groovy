package com.fredpolicarpo.baas.business.entities

import com.fredpolicarpo.baas.business.exceptions.InvalidOperationTypeException

import java.util.stream.Stream

enum OperationType {
    CASH_PAYMENT(1),
    INSTALLMENT_PAYMENT(2),
    WITHDRAW(3),
    DEPOSIT(4),

    int code

    OperationType(final int code) {
        this.code = code
    }

    static OperationType fromCode(final int code) {
       return Stream.of(values())
               .filter({ot -> ot.code == code})
               .findAny()
               .orElseThrow({new InvalidOperationTypeException(code)})
    }
}