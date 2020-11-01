package com.fredpolicarpo.baas.application.spring.adapters


import com.fredpolicarpo.baas.business.exceptions.AccountNotFoundException
import com.fredpolicarpo.baas.ui.GetAccountResponse
import com.fredpolicarpo.baas.ui.api.GetAccountResponseApi
import groovy.transform.Canonical

import javax.servlet.http.HttpServletResponse

@Canonical
class GetAccountPresenter implements com.fredpolicarpo.baas.ui.api.ports.GetAccountPresenter {

    @Override
    GetAccountResponseApi buildApiResponse(GetAccountResponse response) {
        return new GetAccountResponseApi(response, HttpServletResponse.SC_OK)
    }

    @Override
    GetAccountResponseApi buildApiResponse(Exception exception) {
        final int status
        final GetAccountResponse response = new GetAccountResponse(error: exception.message)

        if (exception instanceof AccountNotFoundException) {
            status = HttpServletResponse.SC_NOT_FOUND
        } else {
            status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR
        }

        return (new GetAccountResponseApi(response, status))
    }
}
