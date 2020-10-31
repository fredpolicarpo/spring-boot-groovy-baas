package com.fredpolicarpo.baas.business.exceptions

import groovy.transform.Canonical

@Canonical
class AccountNotFoundException extends Exception {
     final Long id

     @Override
     String getMessage() {
          return "Any Account found with id = ${id}"
     }
}
