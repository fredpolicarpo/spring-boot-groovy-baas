package com.fredpolicarpo.baas.ui.api.presenters

import com.fredpolicarpo.baas.business.exceptions.DuplicatedAccountNumberException
import com.fredpolicarpo.baas.business.exceptions.InvalidDocumentNumberException
import com.fredpolicarpo.baas.ui.CreateAccountResponse
import spock.lang.Specification

import javax.servlet.http.HttpServletResponse

import static com.fredpolicarpo.baas.ui.api.presenters.CreateAccountPresenter.buildCreateAccountResponse

class CreateAccountPresenterSpec extends Specification {

    void "Should return status code CREATED when an account created"() {
        given:
        final CreateAccountResponse response = new CreateAccountResponse(documentNumber: "any", accountId: 1L)

        expect:
        HttpServletResponse.SC_CREATED == buildCreateAccountResponse { response }.httpStatus
    }

    void "Should return the account data when an account created"() {
        given:
        final CreateAccountResponse response = new CreateAccountResponse(documentNumber: "any", accountId: 1L)

        expect:
        response == buildCreateAccountResponse { response }.response
    }

    void "Should return status code CONFLICT when DuplicatedAccountNumberException is raised"() {
        given:
        final DuplicatedAccountNumberException exception = new DuplicatedAccountNumberException()

        expect:
        HttpServletResponse.SC_CONFLICT == buildCreateAccountResponse { throw exception }.httpStatus
    }

    void "Should return status code BAD_REQUEST when InvalidDocumentNumberException is raised"() {
        given:
        final InvalidDocumentNumberException exception = new InvalidDocumentNumberException()

        expect:
        HttpServletResponse.SC_BAD_REQUEST == buildCreateAccountResponse { throw exception }.httpStatus
    }

    void "Should return error message when an exception is raised"() {
        given:
        final String message = "Bad news man!!!"
        final Exception exception = new Exception(message)

        expect:
        message == buildCreateAccountResponse { throw exception }.response.error
    }

    void "Should return status code INTERNAL_SERVER_ERROR when a generic exception is raised"() {
        given:
        final Exception exception = new Exception()

        expect:
        HttpServletResponse.SC_INTERNAL_SERVER_ERROR == buildCreateAccountResponse { throw exception }.httpStatus
    }
}
