package com.fredpolicarpo.baas.business.exceptions

import groovy.transform.Canonical

@Canonical
class InvalidOperationTypeException extends Exception {
    final int code

    @Override
    String getMessage() {
        return "Invalid operation type code: ${code}"
    }
}
