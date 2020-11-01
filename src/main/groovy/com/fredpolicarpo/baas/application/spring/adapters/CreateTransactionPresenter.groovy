package com.fredpolicarpo.baas.application.spring.adapters

import com.fredpolicarpo.baas.business.exceptions.AccountNotFoundException
import com.fredpolicarpo.baas.ui.CreateTransactionResponse
import com.fredpolicarpo.baas.ui.api.CreateTransactionResponseApi
import groovy.transform.Canonical

import javax.servlet.http.HttpServletResponse

@Canonical
class CreateTransactionPresenter implements com.fredpolicarpo.baas.ui.api.ports.CreateTransactionPresenter {

    @Override
    CreateTransactionResponseApi buildApiResponse(CreateTransactionResponse response) {
        return new CreateTransactionResponseApi(response, HttpServletResponse.SC_CREATED)
    }

    @Override
    CreateTransactionResponseApi buildApiResponse(Exception exception) {
        final int status
        final CreateTransactionResponse response = new CreateTransactionResponse(error: exception.message)

        if (exception instanceof AccountNotFoundException) {
            status = HttpServletResponse.SC_CONFLICT
        } else {
            status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR
        }

        return new CreateTransactionResponseApi(response, status)
    }
}
