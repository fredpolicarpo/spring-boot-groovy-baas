package com.fredpolicarpo.baas.ui.api.presenters

import com.fredpolicarpo.baas.business.exceptions.AccountNotFoundException
import com.fredpolicarpo.baas.business.exceptions.InsuficientLimitException
import com.fredpolicarpo.baas.ui.CreateTransactionResponse
import com.fredpolicarpo.baas.ui.api.CreateTransactionResponseApi
import groovy.transform.Canonical
import groovy.util.logging.Slf4j

import javax.servlet.http.HttpServletResponse

@Slf4j
@Canonical
final class CreateTransactionPresenter  {

    static CreateTransactionResponseApi buildCreateTransactionResponse(Closure<CreateTransactionResponse> createTransaction) {
        try {
            return buildApiResponse(createTransaction())
        } catch (final Exception ex) {
            log.error("Fail to create transaction", ex)
            return buildApiResponse(ex)
        }
    }

    private static CreateTransactionResponseApi buildApiResponse(CreateTransactionResponse response) {
        return new CreateTransactionResponseApi(response, HttpServletResponse.SC_CREATED)
    }

    private static CreateTransactionResponseApi buildApiResponse(Exception exception) {
        final int status
        final CreateTransactionResponse response = new CreateTransactionResponse(error: exception.message)

        if (exception instanceof AccountNotFoundException || exception instanceof InsuficientLimitException) {
            status = HttpServletResponse.SC_CONFLICT
        } else {
            status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR
        }

        return new CreateTransactionResponseApi(response, status)
    }
}
