package com.fredpolicarpo.baas.ui.api.presenters


import com.fredpolicarpo.baas.business.exceptions.AccountNotFoundException
import com.fredpolicarpo.baas.ui.GetAccountResponse
import com.fredpolicarpo.baas.ui.api.GetAccountResponseApi
import groovy.transform.Canonical
import groovy.util.logging.Slf4j

import javax.servlet.http.HttpServletResponse

@Slf4j
@Canonical
final class GetAccountPresenter {

    static GetAccountResponseApi buildGetAccountResponse(Closure<GetAccountResponse> action) {
        try {
            return buildApiResponse(action())
        } catch (final Exception ex) {
            log.error("Fail to get account", ex)
            return buildApiResponse(ex)
        }
    }

    private static GetAccountResponseApi buildApiResponse(GetAccountResponse response) {
        return new GetAccountResponseApi(response, HttpServletResponse.SC_OK)
    }

    private static GetAccountResponseApi buildApiResponse(Exception exception) {
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
