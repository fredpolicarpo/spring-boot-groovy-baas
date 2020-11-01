package com.fredpolicarpo.baas.application.spring

import com.fredpolicarpo.baas.application.spring.adapters.GetAccountPresenter
import com.fredpolicarpo.baas.business.exceptions.AccountNotFoundException
import com.fredpolicarpo.baas.ui.GetAccountResponse
import spock.lang.Specification

import javax.servlet.http.HttpServletResponse

class GetAccountPresenterServletResponseSpec  extends Specification  {
    final static GetAccountPresenter getAccountPresenter = new GetAccountPresenter()

    void "Should return status code OK when an account is found"() {
        given:
        final GetAccountResponse getAccountResponse = new GetAccountResponse(documentNumber: "any", accountId: 1L)

        expect:
        HttpServletResponse.SC_OK == getAccountPresenter.buildApiResponse(getAccountResponse).httpStatus
    }

    void "Should return the account data when an account is found"() {
        given:
        final GetAccountResponse getAccountResponse = new GetAccountResponse(documentNumber: "any", accountId: 1L)

        expect:
        getAccountResponse == getAccountPresenter.buildApiResponse(getAccountResponse).response
    }

    void "Should return status code NOT_FOUND when an account is not found"() {
        given:
        final AccountNotFoundException exception = new AccountNotFoundException()

        expect:
        HttpServletResponse.SC_NOT_FOUND == getAccountPresenter.buildApiResponse(exception).httpStatus
    }

    void "Should return error message when an exception is given"() {
        given:
        final String message = "Bad news man!!!"
        final Exception exception = new Exception(message)

        expect:
        message == getAccountPresenter.buildApiResponse(exception).response.error
    }

    void "Should return status code INTERNAL_SERVER_ERROR when a generic exception is given"() {
        given:
        final Exception exception = new Exception()

        expect:
        HttpServletResponse.SC_INTERNAL_SERVER_ERROR == getAccountPresenter.buildApiResponse(exception).httpStatus
    }
}
