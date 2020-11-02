package com.fredpolicarpo.baas.application.spring.adapters

import com.fredpolicarpo.baas.application.spring.adapters.CreateAccountPresenter
import com.fredpolicarpo.baas.business.exceptions.DuplicatedAccountNumberException
import com.fredpolicarpo.baas.business.exceptions.InvalidDocumentNumberException
import com.fredpolicarpo.baas.ui.CreateAccountResponse
import spock.lang.Specification

import javax.servlet.http.HttpServletResponse

class CreateAccountPresenterSpec extends Specification  {
    final static CreateAccountPresenter createAccountPresenter = new CreateAccountPresenter()

    void "Should return status code CREATED when an account created"() {
        given:
        final CreateAccountResponse response = new CreateAccountResponse(documentNumber: "any", accountId: 1L)

        expect:
        HttpServletResponse.SC_CREATED == createAccountPresenter.buildApiResponse(response).httpStatus
    }

    void "Should return the account data when an account created"() {
        given:
        final CreateAccountResponse response = new CreateAccountResponse(documentNumber: "any", accountId: 1L)

        expect:
        response == createAccountPresenter.buildApiResponse(response).response
    }

    void "Should return status code CONFLICT when DuplicatedAccountNumberException is raised"() {
        given:
        final DuplicatedAccountNumberException exception = new DuplicatedAccountNumberException()

        expect:
        HttpServletResponse.SC_CONFLICT == createAccountPresenter.buildApiResponse(exception).httpStatus
    }

    void "Should return status code BAD_REQUEST when InvalidDocumentNumberException is raised"() {
        given:
        final InvalidDocumentNumberException exception = new InvalidDocumentNumberException()

        expect:
        HttpServletResponse.SC_BAD_REQUEST == createAccountPresenter.buildApiResponse(exception).httpStatus
    }

    void "Should return error message when an exception is raised"() {
        given:
        final String message = "Bad news man!!!"
        final Exception exception = new Exception(message)

        expect:
        message == createAccountPresenter.buildApiResponse(exception).response.error
    }

    void "Should return status code INTERNAL_SERVER_ERROR when a generic exception is raised"() {
        given:
        final Exception exception = new Exception()

        expect:
        HttpServletResponse.SC_INTERNAL_SERVER_ERROR == createAccountPresenter.buildApiResponse(exception).httpStatus
    }
}
