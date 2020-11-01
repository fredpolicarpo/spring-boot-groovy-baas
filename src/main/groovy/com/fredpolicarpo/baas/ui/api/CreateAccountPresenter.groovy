package com.fredpolicarpo.baas.ui.api

import com.fredpolicarpo.baas.ui.CreateAccountResponse

interface CreateAccountPresenter {
    CreateAccountResponseApi buildApiResponse(final CreateAccountResponse response)

    CreateAccountResponseApi buildApiResponse(final Exception exception)
}