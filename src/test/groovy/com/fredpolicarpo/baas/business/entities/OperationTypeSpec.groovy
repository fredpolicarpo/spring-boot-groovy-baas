package com.fredpolicarpo.baas.business.entities


import com.fredpolicarpo.baas.business.exceptions.InvalidOperationTypeException
import spock.lang.Specification

class OperationTypeSpec extends Specification {
    void "Show throw InvalidOperationType when an invalid code is given"() {
        given:
        final int invalidCode = 99

        when:
        OperationType.fromCode(invalidCode)

        then:
        final InvalidOperationTypeException ex = thrown(InvalidOperationTypeException)
        ex.code == invalidCode
        ex.message == "Invalid operation type code: ${invalidCode}"
    }

    void "Show return OperationType when a valid code is given"() {
        expect:
        operationCode == OperationType.fromCode(code)

        where:
        code || operationCode
        1    || OperationType.CASH_PAYMENT
        2    || OperationType.INSTALLMENT_PAYMENT
        3    || OperationType.WITHDRAW
        4    || OperationType.DEPOSIT
    }
}
