package com.fredpolicarpo.baas.ui.api.ports

import com.fredpolicarpo.baas.ui.GetAccountResponse
import com.fredpolicarpo.baas.ui.api.GetAccountResponseApi

interface GetAccountPresenter {
    GetAccountResponseApi buildApiResponse(final GetAccountResponse response)

    GetAccountResponseApi buildApiResponse(final Exception exception)
}