package com.fredpolicarpo.baas.application.spring.web

import com.fredpolicarpo.baas.application.spring.repository.AccountJpa
import com.fredpolicarpo.baas.application.spring.repository.IAccountRepositoryJpa
import com.fredpolicarpo.baas.application.spring.repository.ITransactionRepositoryJpa
import com.fredpolicarpo.baas.application.spring.repository.TransactionJpa
import com.fredpolicarpo.baas.business.entities.OperationType
import com.fredpolicarpo.baas.ui.CreateTransactionRequest
import com.fredpolicarpo.baas.ui.CreateTransactionResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.ResponseEntity
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TransactionControllerSpec extends Specification {
    @LocalServerPort
    int port

    @Autowired
    TestRestTemplate restTemplate

    @Autowired
    ITransactionRepositoryJpa transactionRepositoryJpa

    @Autowired
    IAccountRepositoryJpa accountRepositoryJpa

    def cleanup() {
        transactionRepositoryJpa.deleteAllInBatch()
        accountRepositoryJpa.deleteAllInBatch()
    }

    def "Should create a new Transaction"() {
        given:
        final AccountJpa account = new AccountJpa(documentNumber: "1233456987")
        final AccountJpa persistedAccount = accountRepositoryJpa.save(account)
        final String accountId = String.valueOf(persistedAccount.id)

        expect:
        final ResponseEntity<CreateTransactionResponse> response = this.restTemplate.postForEntity("http://localhost:${port}/v1/transactions",
                new CreateTransactionRequest(accountId, operationType, amount),
                CreateTransactionResponse.class
        )
        response.body.transactionId == String.valueOf(id)
        response.body.eventDate != null
        final Optional<TransactionJpa> persistedTransactionOpt = transactionRepositoryJpa.findById(id)
        persistedTransactionOpt.isPresent()
        persisteedAmount == persistedTransactionOpt.get().amount
        operationType == String.valueOf(persistedTransactionOpt.get().operationType.code)
        persistedAccount == persistedTransactionOpt.get().account
        response.body.eventDate == persistedTransactionOpt.get().eventDate.toString()

        where:
        operationType                                          | amount    | id || persisteedAmount
        String.valueOf(OperationType.CASH_PAYMENT.code)        | "10.79"   | 1L || -(new BigDecimal("10.79"))
        String.valueOf(OperationType.INSTALLMENT_PAYMENT.code) | "55.12"   | 2L || -(new BigDecimal("55.12"))
        String.valueOf(OperationType.WITHDRAW.code)            | "100.00"  | 3L || -(new BigDecimal("100.00"))
        String.valueOf(OperationType.DEPOSIT.code)             | "1000.00" | 4L || new BigDecimal("1000.00")
    }
}
