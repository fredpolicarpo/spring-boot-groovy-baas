package com.fredpolicarpo.baas.application.spring.adapters

import com.fredpolicarpo.baas.application.spring.adapters.CreateTransactionPresenter
import com.fredpolicarpo.baas.business.exceptions.AccountNotFoundException
import com.fredpolicarpo.baas.ui.CreateTransactionResponse
import spock.lang.Specification

import javax.servlet.http.HttpServletResponse

class CreateTransactionPresenterSpec extends Specification  {
    final static CreateTransactionPresenter createTransactionPresenter = new CreateTransactionPresenter()

    void "Should return status code CREATED when an transaction is created"() {
        given:
        final CreateTransactionResponse response = new CreateTransactionResponse(transactionId: 1L, eventDate: "somedate")

        expect:
        HttpServletResponse.SC_CREATED == createTransactionPresenter.buildApiResponse(response).httpStatus
    }

    void "Should return the transaction data when an transaction is created"() {
        given:
        final CreateTransactionResponse response = new CreateTransactionResponse(transactionId: 1L, eventDate: "somedate")

        expect:
        response == createTransactionPresenter.buildApiResponse(response).response
    }

    void "Should return error message when an exception is given"() {
        given:
        final String message = "Bad news man!!!"
        final Exception exception = new Exception(message)

        expect:
        message == createTransactionPresenter.buildApiResponse(exception).response.error
    }

    void "Should return status code INTERNAL_SERVER_ERROR when a generic exception is raised"() {
        given:
        final Exception exception = new Exception()

        expect:
        HttpServletResponse.SC_INTERNAL_SERVER_ERROR == createTransactionPresenter.buildApiResponse(exception).httpStatus
    }

    void "Should return status code CONFLICT when a AccountNotFoundException is raised"() {
        given:
        final Exception exception = new AccountNotFoundException()

        expect:
        HttpServletResponse.SC_CONFLICT == createTransactionPresenter.buildApiResponse(exception).httpStatus
    }

}
