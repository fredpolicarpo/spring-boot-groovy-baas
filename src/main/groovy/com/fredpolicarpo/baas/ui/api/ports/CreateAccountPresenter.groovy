package com.fredpolicarpo.baas.ui.api.ports

import com.fredpolicarpo.baas.ui.CreateAccountResponse
import com.fredpolicarpo.baas.ui.api.CreateAccountResponseApi

interface CreateAccountPresenter {
    CreateAccountResponseApi buildApiResponse(final CreateAccountResponse response)

    CreateAccountResponseApi buildApiResponse(final Exception exception)
}