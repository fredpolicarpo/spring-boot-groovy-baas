package com.fredpolicarpo.baas.application.spring.web

import com.fredpolicarpo.baas.business.Interactor
import com.fredpolicarpo.baas.ui.CreateTransactionRequest
import com.fredpolicarpo.baas.ui.CreateTransactionResponse
import com.fredpolicarpo.baas.ui.api.CreateTransactionResponseApi
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.servlet.http.HttpServletResponse

import static com.fredpolicarpo.baas.ui.api.presenters.CreateTransactionPresenter.buildCreateTransactionResponse


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

}
