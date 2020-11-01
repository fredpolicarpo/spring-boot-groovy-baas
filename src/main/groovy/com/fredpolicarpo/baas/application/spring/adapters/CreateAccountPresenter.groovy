package com.fredpolicarpo.baas.application.spring.adapters


import com.fredpolicarpo.baas.business.exceptions.DuplicatedAccountNumberException
import com.fredpolicarpo.baas.business.exceptions.InvalidDocumentNumberException
import com.fredpolicarpo.baas.ui.CreateAccountResponse
import com.fredpolicarpo.baas.ui.api.CreateAccountResponseApi
import groovy.transform.Canonical

import javax.servlet.http.HttpServletResponse

@Canonical
class CreateAccountPresenter implements com.fredpolicarpo.baas.ui.api.ports.CreateAccountPresenter {

    @Override
    CreateAccountResponseApi buildApiResponse(CreateAccountResponse response) {
        return new CreateAccountResponseApi(response, HttpServletResponse.SC_CREATED)
    }

    @Override
    CreateAccountResponseApi buildApiResponse(Exception exception) {
        final int status
        final CreateAccountResponse response = new CreateAccountResponse(error: exception.message)

        if (exception instanceof DuplicatedAccountNumberException) {
            status = HttpServletResponse.SC_CONFLICT
        } else if (exception instanceof InvalidDocumentNumberException) {
            status = HttpServletResponse.SC_BAD_REQUEST
        } else {
            status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR
        }

        return new CreateAccountResponseApi(response, status)
    }
}
