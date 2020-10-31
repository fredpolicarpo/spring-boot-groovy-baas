package com.fredpolicarpo.baas


import com.fredpolicarpo.baas.business.exceptions.InvalidDocumentNumberException
import com.fredpolicarpo.baas.business.ports.AccountRepository
import com.fredpolicarpo.baas.business.exceptions.DuplicatedAccountNumberException
import com.fredpolicarpo.baas.business.ports.Timer
import com.fredpolicarpo.baas.business.Interactor
import com.fredpolicarpo.baas.business.entities.Account
import com.fredpolicarpo.baas.ui.CreateAccountRequest
import com.fredpolicarpo.baas.ui.CreateAccountResponse
import spock.lang.Specification

import java.time.Instant

class InteractorSpec extends Specification {
    final static String currentInstantStr = "2020-10-31T10:00:00Z"
    final static Instant fixedCurrentInstant = Instant.parse(currentInstantStr)

    AccountRepository mockAccountRepository
    Interactor interactor

    def setup() {
        final Timer mockTimer = new TimerMock(fixedCurrentInstant)
        mockAccountRepository = new AccountRepositoryMock()
        interactor = new Interactor(mockAccountRepository, mockTimer)
    }

    def "Should create a new Account"() {
        given:
        final String documentNumber = "11122233344456"
        final CreateAccountRequest createAccountRequest = new CreateAccountRequest(documentNumber)

        expect: "Receive a response with the correct data"
        final CreateAccountResponse response = interactor.createAccount(createAccountRequest)
        response.documentNumber == documentNumber
        response.createdAt == currentInstantStr

        and: "The account was persisted on the database"
        final Optional<Account> savedAccount = mockAccountRepository.findByDocumentNumber(documentNumber)
        savedAccount.isPresent()
        savedAccount.get().documentNumber == documentNumber
    }

    def "Should not allow create account with duplicated document number"() {
        given:
        final String documentNumber = "11122233344456"
        final CreateAccountRequest createAccountRequest1 = new CreateAccountRequest(documentNumber)
        final CreateAccountRequest createAccountRequest2 = new CreateAccountRequest(documentNumber)

        when:
        interactor.createAccount(createAccountRequest1)
        interactor.createAccount(createAccountRequest2)

        then:
        final DuplicatedAccountNumberException ex = thrown(DuplicatedAccountNumberException)
        ex.documentNumber == documentNumber
        ex.message == "Cannot create account. Already exists an account with document number = ${documentNumber}"
    }

    def "Should not allow create account with empty document number"() {
        when:
        interactor.createAccount(new CreateAccountRequest(documentNumber))

        then:
        final InvalidDocumentNumberException ex = thrown(InvalidDocumentNumberException)
        ex.documentNumber == documentNumber
        ex.message == message

        where:
        documentNumber | message
        null           | "Invalid document number: null"
        ""             | "Invalid document number: ''"
        "            " | "Invalid document number: '            '"
    }

}