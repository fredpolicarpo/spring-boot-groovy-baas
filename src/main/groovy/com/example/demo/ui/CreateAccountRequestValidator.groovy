package com.example.demo.ui

import com.example.demo.business.exceptions.InvalidDocumentNumberException
import com.example.demo.ui.CreateAccountRequest
import org.springframework.util.StringUtils

class CreateAccountRequestValidator {

    static void validate(final CreateAccountRequest createAccountRequest) {
        if(!createAccountRequest.documentNumber || !StringUtils.hasText(createAccountRequest.documentNumber)) {
            throw new InvalidDocumentNumberException(createAccountRequest.documentNumber)
        }
    }
}
