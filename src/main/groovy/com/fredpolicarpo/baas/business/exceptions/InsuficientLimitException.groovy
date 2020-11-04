package com.fredpolicarpo.baas.business.exceptions

class InsuficientLimitException extends Exception {
    BigDecimal creditLimit
    BigDecimal transactionAmount
}
