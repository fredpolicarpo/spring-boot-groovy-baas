package com.fredpolicarpo.baas.ui.api

import com.fredpolicarpo.baas.ui.GetAccountResponse
import groovy.transform.Canonical

@Canonical
class GetAccountResponseApi {
    GetAccountResponse response
    int httpStatus
}
