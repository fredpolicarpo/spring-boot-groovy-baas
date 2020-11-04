package com.fredpolicarpo.baas.business.entities

import groovy.transform.Canonical

@Canonical
class Account {
    Long id
    String documentNumber
    BigDecimal creditLimit = new BigDecimal("0.00")
}