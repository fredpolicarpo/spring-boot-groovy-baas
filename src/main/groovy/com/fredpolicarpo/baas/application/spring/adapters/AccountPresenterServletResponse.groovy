package com.fredpolicarpo.baas.application.spring.adapters

import com.fasterxml.jackson.databind.ObjectMapper
import com.fredpolicarpo.baas.business.exceptions.AccountNotFoundException
import com.fredpolicarpo.baas.business.exceptions.DuplicatedAccountNumberException
import com.fredpolicarpo.baas.business.exceptions.InvalidDocumentNumberException
import com.fredpolicarpo.baas.ui.CreateAccountResponse
import com.fredpolicarpo.baas.ui.GetAccountResponse
import com.fredpolicarpo.baas.ui.api.CreateAccountPresenter
import com.fredpolicarpo.baas.ui.api.GetAccountPresenter
import groovy.transform.Canonical

import javax.servlet.http.HttpServletResponse

@Canonical
class AccountPresenterServletResponse implements GetAccountPresenter, CreateAccountPresenter {
    final HttpServletResponse servletResponse
    private static final ObjectMapper objectMapper = new ObjectMapper()

    @Override
    void showAccount(GetAccountResponse response) {
        servletResponse.setStatus(HttpServletResponse.SC_OK)
        sendObjectAsJson(response)
    }

    @Override
    void showAccount(CreateAccountResponse response) {
        servletResponse.setStatus(HttpServletResponse.SC_CREATED)
        sendObjectAsJson(response)
    }

    @Override
    void showError(Exception exception) {
        if (exception instanceof AccountNotFoundException) {
            servletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND)
        } else if (exception instanceof DuplicatedAccountNumberException) {
            servletResponse.setStatus(HttpServletResponse.SC_CONFLICT)
        } else if (exception instanceof InvalidDocumentNumberException) {
            servletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST)
        }

        sendObjectAsJson(new GetAccountResponse(error: exception.message))
    }

    private void sendObjectAsJson(final Object response) {
        final String json = objectMapper.writeValueAsString(response)

        PrintWriter out = servletResponse.getWriter()
        servletResponse.setContentType("application/json")
        servletResponse.setCharacterEncoding("UTF-8")
        out.print(json)
        out.flush()
    }
}
