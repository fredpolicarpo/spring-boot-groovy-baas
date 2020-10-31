package com.fredpolicarpo.baas.application.spring.controller


import com.fredpolicarpo.baas.business.Interactor
import com.fredpolicarpo.baas.business.exceptions.DuplicatedAccountNumberException
import com.fredpolicarpo.baas.business.exceptions.InvalidDocumentNumberException
import com.fredpolicarpo.baas.ui.CreateAccountRequest
import com.fredpolicarpo.baas.ui.CreateAccountResponse
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
        try {
            final CreateAccountResponse createAccountResponse = interactor.createAccount(request)
            response.setStatus(HttpServletResponse.SC_CREATED)
            return createAccountResponse
        } catch (final DuplicatedAccountNumberException duplicatedAccountEx) {
            response.setStatus(HttpServletResponse.SC_CONFLICT)
            return new CreateAccountResponse(error: duplicatedAccountEx.message)
        } catch (final InvalidDocumentNumberException invalidDocumentNumberEx) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST)
            return new CreateAccountResponse(error: invalidDocumentNumberEx.message)
        } catch (final Exception ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
            return new CreateAccountResponse(error: ex.message)
        }
    }
}
