package com.example.demo.business.exceptions

import groovy.transform.Canonical

@Canonical
class DuplicatedAccountNumberException extends Exception {
    final String documentNumber

    @Override
    String getMessage() {
        return "Cannot create account. Already exists an account with document number = ${documentNumber}"
    }
}
