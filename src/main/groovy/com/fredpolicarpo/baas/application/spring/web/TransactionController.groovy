package com.fredpolicarpo.baas.application.spring.web

import com.fredpolicarpo.baas.business.Interactor
import com.fredpolicarpo.baas.ui.CreateTransactionRequest
import com.fredpolicarpo.baas.ui.CreateTransactionResponse
import com.fredpolicarpo.baas.ui.api.CreateTransactionResponseApi
import com.fredpolicarpo.baas.ui.api.ports.CreateTransactionPresenter
import groovy.util.logging.Slf4j
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.servlet.http.HttpServletResponse

@Slf4j
@RestController
@RequestMapping("/v1/transactions")
class TransactionController {

    final Interactor interactor

    TransactionController(final Interactor interactor) {
        this.interactor = interactor
    }

    @PostMapping
    CreateTransactionResponse createTransaction(@RequestBody CreateTransactionRequest request, HttpServletResponse response) {
        final CreateTransactionResponseApi createTransactionResponseApi = buildCreateTransactionResponse({ interactor.createTransaction(request) })
        response.setStatus(createTransactionResponseApi.httpStatus)
        return createTransactionResponseApi.response
    }

    private static CreateTransactionResponseApi buildCreateTransactionResponse(Closure<CreateTransactionResponse> action) {
        final CreateTransactionPresenter presenter = new com.fredpolicarpo.baas.application.spring.adapters.CreateTransactionPresenter()
        try {
            return presenter.buildApiResponse(action())
        } catch (final Exception ex) {
            log.error("Fail to create transaction", ex)
            return presenter.buildApiResponse(ex)
        }
    }

}
