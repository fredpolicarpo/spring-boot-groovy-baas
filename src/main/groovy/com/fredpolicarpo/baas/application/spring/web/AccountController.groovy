package com.fredpolicarpo.baas.application.spring.web

import com.fredpolicarpo.baas.application.spring.adapters.AccountPresenterServletResponse
import com.fredpolicarpo.baas.business.Interactor
import com.fredpolicarpo.baas.ui.CreateAccountRequest
import com.fredpolicarpo.baas.ui.CreateAccountResponse
import com.fredpolicarpo.baas.ui.GetAccountResponse
import com.fredpolicarpo.baas.ui.api.CreateAccountPresenter
import com.fredpolicarpo.baas.ui.api.GetAccountPresenter
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
    void getAccount(@RequestBody CreateAccountRequest request, HttpServletResponse response) {
       final CreateAccountPresenter presenter = new AccountPresenterServletResponse(response)

        try {
            presenter.showAccount(interactor.createAccount(request))
        } catch (final Exception ex) {
            presenter.showError(ex)
        }
    }

    @GetMapping("/{accountId}")
    void getAccount(@PathVariable Long accountId, HttpServletResponse response) {
        final GetAccountPresenter presenter = new AccountPresenterServletResponse(response)

        try {
            presenter.showAccount(interactor.getAccount(accountId))
        } catch (final Exception ex) {
            presenter.showError(ex)
        }
    }
}
