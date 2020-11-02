package com.fredpolicarpo.baas.application.spring.web


import com.fredpolicarpo.baas.business.Interactor
import com.fredpolicarpo.baas.ui.CreateAccountRequest
import com.fredpolicarpo.baas.ui.CreateAccountResponse
import com.fredpolicarpo.baas.ui.GetAccountResponse
import com.fredpolicarpo.baas.ui.api.ports.CreateAccountPresenter
import com.fredpolicarpo.baas.ui.api.CreateAccountResponseApi
import com.fredpolicarpo.baas.ui.api.ports.GetAccountPresenter
import com.fredpolicarpo.baas.ui.api.GetAccountResponseApi
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.servlet.http.HttpServletResponse

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

    private static CreateAccountResponseApi buildCreateAccountResponse(Closure<CreateAccountResponse> action) {
        final CreateAccountPresenter presenter = new com.fredpolicarpo.baas.application.spring.adapters.CreateAccountPresenter()
        try {
            return presenter.buildApiResponse(action())
        } catch (final Exception ex) {
            return presenter.buildApiResponse(ex)
        }
    }

    @GetMapping("/{accountId}")
    GetAccountResponse getAccount(@PathVariable Long accountId, HttpServletResponse response) {
        final GetAccountResponseApi getAccountResponseApi = buildGetAccountResponse({ interactor.getAccount(accountId) })
        response.setStatus(getAccountResponseApi.httpStatus)
        return getAccountResponseApi.response
    }

    private static GetAccountResponseApi buildGetAccountResponse(Closure<GetAccountResponse> action) {
        final GetAccountPresenter presenter = new com.fredpolicarpo.baas.application.spring.adapters.GetAccountPresenter()

        try {
            return presenter.buildApiResponse(action())
        } catch (final Exception ex) {
            return presenter.buildApiResponse(ex)
        }
    }
}
