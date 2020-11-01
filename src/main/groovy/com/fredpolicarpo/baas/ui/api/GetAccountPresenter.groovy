package com.fredpolicarpo.baas.ui.api

import com.fredpolicarpo.baas.ui.GetAccountResponse

interface GetAccountPresenter {
    GetAccountResponseApi buildApiResponse(final GetAccountResponse response)

    GetAccountResponseApi buildApiResponse(final Exception exception)
}