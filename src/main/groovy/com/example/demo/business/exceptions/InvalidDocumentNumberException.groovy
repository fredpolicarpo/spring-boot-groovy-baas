package com.example.demo.business.exceptions

import groovy.transform.Canonical

@Canonical
class InvalidDocumentNumberException extends Exception {
    final String documentNumber

    @Override
    String getMessage() {
        return "Invalid document number: ${documentNumber == null ? null : "'${documentNumber}'"}"
    }
}
