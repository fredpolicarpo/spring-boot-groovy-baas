package com.example.demo

import com.example.demo.business.entities.Account
import com.example.demo.business.entities.OperationType
import com.example.demo.business.entities.Transaction
import com.example.demo.business.services.BankTransaction
import spock.lang.Specification

class BankTransactionSpec extends Specification {

    BankTransaction bankTransaction = new BankTransaction()

    def "Compra a vista deve registrar uma transação com valor negativo"() {
        given:
        final Account account = new Account(documentNumber: '09887678786')
        final OperationType operationType = OperationType.COMPRA_A_VISTA
        final BigDecimal amount = new BigDecimal("10.50")

        when:
        Transaction transaction = bankTransaction.execute(operationType, amount, account)

        then:
        new BigDecimal("-10.50") == transaction.amount
    }
}
