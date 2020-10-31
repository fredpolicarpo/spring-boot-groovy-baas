package com.fredpolicarpo.baas.ui


import com.fredpolicarpo.baas.business.exceptions.InvalidDocumentNumberException
import org.springframework.util.StringUtils

class CreateAccountRequestValidator {

    static void validate(final CreateAccountRequest createAccountRequest) {
        if(!createAccountRequest.documentNumber || !StringUtils.hasText(createAccountRequest.documentNumber)) {
            throw new InvalidDocumentNumberException(createAccountRequest.documentNumber)
        }
    }
}
