package com.fredpolicarpo.baas.business.services

import com.fredpolicarpo.baas.mocks.TimerMock
import com.fredpolicarpo.baas.business.entities.OperationType
import com.fredpolicarpo.baas.business.entities.Account
import com.fredpolicarpo.baas.business.ports.Timer
import spock.lang.Specification

import java.time.Instant

class BankTransactionSpec extends Specification {
    final static Instant fixedCurrentInstant = Instant.parse("2020-10-31T10:00:00Z")
    BankTransaction bankTransaction

    def setup() {
        final Timer mockTimer = new TimerMock(fixedCurrentInstant)
        bankTransaction = new BankTransaction(mockTimer)
    }

    def "Payments and Withdraws transactions should have negative amount"() {
        given:
        final Account account = new Account(documentNumber: '09887678786')

        expect:
        expectedValue == bankTransaction.execute(operationType, amount, account).amount

        where:
        operationType                     | amount                  || expectedValue
        OperationType.CASH_PAYMENT        | new BigDecimal("1.50")  || new BigDecimal("-1.50")
        OperationType.INSTALLMENT_PAYMENT | new BigDecimal("10.50") || new BigDecimal("-10.50")
        OperationType.WITHDRAW            | new BigDecimal("120")   || new BigDecimal("-120")
    }

    def "Deposit transactions should have positive amount"() {
        given:
        final Account account = new Account(documentNumber: '09887678786')
        final OperationType operationType = OperationType.DEPOSIT

        expect:
        expectedValue == bankTransaction.execute(operationType, amount, account).amount

        where:
        amount                   || expectedValue
        new BigDecimal("198.50") || new BigDecimal("198.50")
        new BigDecimal("34.12")  || new BigDecimal("34.12")
    }

    def "Transactions should register the operation type"() {
        given:
        final Account account = new Account(documentNumber: '09887678786')
        final BigDecimal amount = new BigDecimal("1.50")

        expect:
        expectedOperationType == bankTransaction.execute(operationType, amount, account).operationType

        where:
        operationType                     || expectedOperationType
        OperationType.CASH_PAYMENT        || OperationType.CASH_PAYMENT
        OperationType.INSTALLMENT_PAYMENT || OperationType.INSTALLMENT_PAYMENT
        OperationType.WITHDRAW            || OperationType.WITHDRAW
        OperationType.DEPOSIT             || OperationType.DEPOSIT
    }

    def "Transactions should register the account"() {
        given:
        final Account account = new Account(documentNumber: '09887678786')
        final BigDecimal amount = new BigDecimal("1.50")

        expect:
        account == bankTransaction.execute(operationType, amount, account).account

        where:
        operationType                     || expectedOperationType
        OperationType.CASH_PAYMENT        || OperationType.CASH_PAYMENT
        OperationType.INSTALLMENT_PAYMENT || OperationType.INSTALLMENT_PAYMENT
        OperationType.WITHDRAW            || OperationType.WITHDRAW
        OperationType.DEPOSIT             || OperationType.DEPOSIT
    }

    def "Transactions should register the event time as current instant"() {
        given:
        final Account account = new Account(documentNumber: '09887678786')
        final BigDecimal amount = new BigDecimal("1.50")

        expect:
        expetedEventDate == bankTransaction.execute(operationType, amount, account).eventDate

        where:
        operationType                     || expetedEventDate
        OperationType.CASH_PAYMENT        || fixedCurrentInstant
        OperationType.INSTALLMENT_PAYMENT || fixedCurrentInstant
        OperationType.WITHDRAW            || fixedCurrentInstant
        OperationType.DEPOSIT             || fixedCurrentInstant
    }
}
