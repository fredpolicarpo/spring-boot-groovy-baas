package com.fredpolicarpo.baas.ui.api

import com.fredpolicarpo.baas.ui.CreateAccountResponse
import groovy.transform.Canonical

@Canonical
class CreateAccountResponseApi {
    CreateAccountResponse response
    int httpStatus
}
