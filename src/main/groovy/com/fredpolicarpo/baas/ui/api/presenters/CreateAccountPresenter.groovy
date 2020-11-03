package com.fredpolicarpo.baas.ui.api.presenters


import com.fredpolicarpo.baas.business.exceptions.DuplicatedAccountNumberException
import com.fredpolicarpo.baas.business.exceptions.InvalidDocumentNumberException
import com.fredpolicarpo.baas.ui.CreateAccountResponse
import com.fredpolicarpo.baas.ui.api.CreateAccountResponseApi
import groovy.transform.Canonical
import groovy.util.logging.Slf4j

import javax.servlet.http.HttpServletResponse

@Slf4j
@Canonical
final class CreateAccountPresenter  {

    static CreateAccountResponseApi buildCreateAccountResponse(Closure<CreateAccountResponse> createAccount) {
        try {
            return buildApiResponse(createAccount())
        } catch (final Exception ex) {
            log.error("Fail to create account", ex)
            return buildApiResponse(ex)
        }
    }

    private static CreateAccountResponseApi buildApiResponse(CreateAccountResponse response) {
        return new CreateAccountResponseApi(response, HttpServletResponse.SC_CREATED)
    }

    private static CreateAccountResponseApi buildApiResponse(Exception exception) {
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
