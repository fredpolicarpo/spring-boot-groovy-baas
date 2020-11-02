package com.fredpolicarpo.baas.application.spring.adapters

import com.fredpolicarpo.baas.application.spring.repository.AccountJpa
import com.fredpolicarpo.baas.application.spring.repository.ITransactionRepositoryJpa
import com.fredpolicarpo.baas.application.spring.repository.TransactionJpa
import com.fredpolicarpo.baas.business.entities.Account
import com.fredpolicarpo.baas.business.entities.Transaction
import com.fredpolicarpo.baas.business.ports.TransactionRepository
import org.springframework.stereotype.Service

@Service
class TransactionRepositoryJpa implements TransactionRepository {
    final ITransactionRepositoryJpa transactionRepositoryJpa

    TransactionRepositoryJpa(ITransactionRepositoryJpa transactionRepositoryJpa) {
        this.transactionRepositoryJpa = transactionRepositoryJpa
    }

    @Override
    Optional<Transaction> findById(Long id) {
        return transactionRepositoryJpa.findById(id)
                .map({ tx -> mapFromDb(tx) })
    }

    static Transaction mapFromDb(final TransactionJpa transactionJpa) {
        final Account account = new Account(
                documentNumber: transactionJpa.account.documentNumber,
                id: transactionJpa.account.id
        )

        return new Transaction(
                id: transactionJpa.id,
                account: account,
                amount: transactionJpa.amount,
                operationType: transactionJpa.operationType,
                eventDate: transactionJpa.eventDate
        )
    }

    @Override
    Transaction save(Transaction transaction) {
        return mapFromDb(transactionRepositoryJpa.save(mapFromEntity(transaction)))
    }

    static TransactionJpa mapFromEntity(final Transaction transaction) {
        final AccountJpa account = new AccountJpa(id: transaction.account.id, documentNumber: transaction.account.documentNumber)

        return new TransactionJpa(
                account: account,
                amount: transaction.amount,
                operationType: transaction.operationType,
                eventDate: transaction.eventDate
        )
    }
}
