package com.fredpolicarpo.baas.application.spring.web


import com.fredpolicarpo.baas.business.Interactor
import com.fredpolicarpo.baas.ui.CreateAccountRequest
import com.fredpolicarpo.baas.ui.CreateAccountResponse
import com.fredpolicarpo.baas.ui.GetAccountResponse
import com.fredpolicarpo.baas.ui.api.CreateAccountResponseApi
import com.fredpolicarpo.baas.ui.api.GetAccountResponseApi
import groovy.util.logging.Slf4j
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.servlet.http.HttpServletResponse

import static com.fredpolicarpo.baas.ui.api.presenters.CreateAccountPresenter.buildCreateAccountResponse
import static com.fredpolicarpo.baas.ui.api.presenters.GetAccountPresenter.buildGetAccountResponse


@Slf4j
@RestController
@RequestMapping("/v1/accounts")
class AccountController {

    final Interactor interactor

    AccountController(final Interactor interactor) {
        this.interactor = interactor
    }

    @PostMapping
    CreateAccountResponse createAccount(@RequestBody CreateAccountRequest request, HttpServletResponse response) {
        final CreateAccountResponseApi createAccountResponseApi = buildCreateAccountResponse({ interactor.createAccount(request) })
        response.setStatus(createAccountResponseApi.httpStatus)
        return createAccountResponseApi.response
    }

    @GetMapping("/{accountId}")
    GetAccountResponse getAccount(@PathVariable Long accountId, HttpServletResponse response) {
        final GetAccountResponseApi getAccountResponseApi = buildGetAccountResponse({ interactor.getAccount(accountId) })
        response.setStatus(getAccountResponseApi.httpStatus)
        return getAccountResponseApi.response
    }
}
