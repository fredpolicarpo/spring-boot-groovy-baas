package com.fredpolicarpo.baas.application.spring.adapters


import com.fredpolicarpo.baas.application.spring.repository.AccountJpa
import com.fredpolicarpo.baas.application.spring.repository.IAccountRepositoryJpa
import com.fredpolicarpo.baas.business.entities.Account
import com.fredpolicarpo.baas.business.ports.AccountRepository
import org.springframework.stereotype.Service

@Service
class AccountRepositoryJpa implements AccountRepository {

    final IAccountRepositoryJpa accountRepositoryJpa

    AccountRepositoryJpa(IAccountRepositoryJpa accountRepositoryJpa) {
        this.accountRepositoryJpa = accountRepositoryJpa
    }

    @Override
    Optional<Account> findByDocumentNumber(String documentNumber) {
        final Optional<AccountJpa> accountJpa = accountRepositoryJpa.findByDocumentNumber(documentNumber)

        return accountJpa.map({a -> new Account(a.documentNumber)})
    }

    Account save(Account account) {
        final AccountJpa saved =  accountRepositoryJpa.save(new AccountJpa(documentNumber: account.documentNumber))

        return new Account(documentNumber:  saved.documentNumber, id: saved.id)
    }

    @Override
    boolean existsAccountWithDocumentNumber(String documentNumber) {
        return accountRepositoryJpa.existsByDocumentNumber(documentNumber)
    }

    @Override
    Optional<Account> findById(Long id) {
        final Optional<AccountJpa> saved =  accountRepositoryJpa.findById(id)

        return saved.map({a -> new Account(documentNumber:  a.documentNumber, id: a.id)})
    }
}
