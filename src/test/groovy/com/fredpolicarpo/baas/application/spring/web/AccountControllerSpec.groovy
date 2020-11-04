package com.fredpolicarpo.baas.application.spring.web

import com.fredpolicarpo.baas.application.spring.repository.AccountJpa
import com.fredpolicarpo.baas.application.spring.repository.IAccountRepositoryJpa
import com.fredpolicarpo.baas.ui.CreateAccountRequest
import com.fredpolicarpo.baas.ui.CreateAccountResponse
import com.fredpolicarpo.baas.ui.GetAccountResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountControllerSpec extends Specification {
    @LocalServerPort
    int port

    @Autowired
    TestRestTemplate restTemplate

    @Autowired
    IAccountRepositoryJpa accountRepositoryJpa

    def cleanup() {
        accountRepositoryJpa.deleteAllInBatch()
    }

    def "Should create a new Account"() {
        given:
        final String documentNumber = "003876321298"
        final String creditLimit = "500.00"
        final CreateAccountRequest request = new CreateAccountRequest(documentNumber, creditLimit)

        when:
        final ResponseEntity<CreateAccountResponse> response = this.restTemplate.postForEntity("http://localhost:${port}/v1/accounts",
                request,
                CreateAccountResponse.class
        )

        then:
        response.statusCode == HttpStatus.CREATED
        response.body.documentNumber == documentNumber
        and:
        final Optional<AccountJpa> savedAccount = accountRepositoryJpa.findByDocumentNumber(documentNumber)
        savedAccount.isPresent()
        savedAccount.get().documentNumber == documentNumber
        savedAccount.get().creditLimit.toString() == creditLimit
    }

    def "Should return status code CONFLICT when try to create an account with a duplicated document number"() {
        given:
        final String documentNumber = "003876321298"
        final CreateAccountRequest request = new CreateAccountRequest(documentNumber)
        and:
        accountRepositoryJpa.save(new AccountJpa(documentNumber: documentNumber))

        when:
        final ResponseEntity<CreateAccountResponse> response = this.restTemplate.postForEntity("http://localhost:${port}/v1/accounts",
                request,
                CreateAccountResponse.class
        )

        then:
        response.statusCode == HttpStatus.CONFLICT
    }

    def "Should return status code BAD_REQUEST when try to create an account with a invalid document number"() {
        when:
        final ResponseEntity<CreateAccountResponse> response = this.restTemplate.postForEntity("http://localhost:${port}/v1/accounts",
                request,
                CreateAccountResponse.class
        )

        then:
        response.statusCode == HttpStatus.BAD_REQUEST

        where:
        request                         || status
        new CreateAccountRequest("   ") || HttpStatus.BAD_REQUEST
        new CreateAccountRequest("")    || HttpStatus.BAD_REQUEST
        new CreateAccountRequest()      || HttpStatus.BAD_REQUEST
    }

    def "Should retrieve a saved Account"() {
        given:
        final String documentNumber = "003876321298"
        final BigDecimal creditLimit = new BigDecimal("1000.00")
        final AccountJpa savedAccount = accountRepositoryJpa.save(new AccountJpa(documentNumber: documentNumber, creditLimit: creditLimit))
        final Long accountId = savedAccount.id

        when:
        final ResponseEntity<GetAccountResponse> response = this.restTemplate.getForEntity("http://localhost:${port}/v1/accounts/${accountId}",
                GetAccountResponse.class
        )

        then:
        response.statusCode == HttpStatus.OK
        response.body.documentNumber == documentNumber
        response.body.accountId == String.valueOf(accountId)
        response.body.creditLimit == creditLimit.toString()
    }

    def "Should return NOT_FOUND when try to retrieve a not existent Account"() {
        when: "Try get an account with an empty database"
        final ResponseEntity<GetAccountResponse> response = this.restTemplate.getForEntity("http://localhost:${port}/v1/accounts/1",
                GetAccountResponse.class
        )

        then:
        response.statusCode == HttpStatus.NOT_FOUND
    }

}
