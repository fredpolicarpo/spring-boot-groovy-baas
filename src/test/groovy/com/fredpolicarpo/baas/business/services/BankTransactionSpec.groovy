package com.fredpolicarpo.baas.business.services


import com.fredpolicarpo.baas.business.entities.Account
import com.fredpolicarpo.baas.business.entities.OperationType
import com.fredpolicarpo.baas.business.exceptions.InsuficientLimitException
import com.fredpolicarpo.baas.business.ports.Timer
import com.fredpolicarpo.baas.business.ports.TransactionRepository
import com.fredpolicarpo.baas.mocks.TimerMock
import com.fredpolicarpo.baas.mocks.TransactionRepositoryMock
import spock.lang.Specification

import java.time.Instant

class BankTransactionSpec extends Specification {
    final static Instant fixedCurrentInstant = Instant.parse("2020-10-31T10:00:00Z")
    BankTransaction bankTransaction


    def setup() {
        final Timer mockTimer = new TimerMock(fixedCurrentInstant)
        bankTransaction = new BankTransaction(mockTimer)
    }


    def "Payments should reduce the available credit limit"() {
        given:
        final Account account = new Account(documentNumber: '09887678786', creditLimit: new BigDecimal("100.00"))
        BigDecimal previousLimit = account.creditLimit

        expect:
        bankTransaction.execute(operationType, amount, account)
        previousLimit - amount == account.creditLimit

        where:
        operationType                     | amount
        OperationType.CASH_PAYMENT        | new BigDecimal("10.50")
        OperationType.INSTALLMENT_PAYMENT | new BigDecimal("14.50")
        OperationType.WITHDRAW            | new BigDecimal("1.57")
    }

    def "Deposit should increase the available credit limit"() {
        given:
        final BigDecimal previousLimit = new BigDecimal("100.00")
        final Account account = new Account(documentNumber: '09887678786', creditLimit: previousLimit)


        expect:
        bankTransaction.execute(operationType, amount, account)
        previousLimit + amount == account.creditLimit

        where:
        operationType         | amount
        OperationType.DEPOSIT | new BigDecimal("1.50")
    }

    def "Payments greater then credit limit should thrown exception"() {
        given:
        final BigDecimal previousLimit = new BigDecimal("100.00")
        final Account account = new Account(documentNumber: '09887678786', creditLimit: previousLimit)

        when:
        bankTransaction.execute(operationType, amount, account)

        then:
        final InsuficientLimitException ex = thrown(InsuficientLimitException)
        previousLimit == ex.creditLimit
        -amount == ex.transactionAmount

        where:
        operationType                     | amount
        OperationType.INSTALLMENT_PAYMENT | new BigDecimal("100.50")
    }

    def "Payments and Withdraws transactions should have negative amount"() {
        given:
        final Account account = new Account(documentNumber: '09887678786', creditLimit: new BigDecimal("1000.00"))

        expect:
        expectedValue == bankTransaction.execute(operationType, amount, account).amount
        expectedValue == bankTransaction.execute(operationType, amount, account).balance

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
        expectedValue == bankTransaction.execute(operationType, amount, account).balance

        where:
        amount                   || expectedValue
        new BigDecimal("198.50") || new BigDecimal("198.50")
        new BigDecimal("34.12")  || new BigDecimal("34.12")
    }

    def "Transactions should register the operation type"() {
        given:
        final Account account = new Account(documentNumber: '09887678786', creditLimit: new BigDecimal("1000.00"))
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
        final Account account = new Account(documentNumber: '09887678786', creditLimit: new BigDecimal("1000.00"))
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
        final Account account = new Account(documentNumber: '09887678786', creditLimit: new BigDecimal("1000.00"))
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
