package com.fredpolicarpo.baas.application.spring


import com.fredpolicarpo.baas.business.Interactor
import com.fredpolicarpo.baas.business.ports.AccountRepository
import com.fredpolicarpo.baas.business.ports.Timer
import com.fredpolicarpo.baas.business.ports.TransactionRepository
import com.fredpolicarpo.baas.business.services.BankTransaction
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@SpringBootApplication
class Application {
    static void main(String[] args) {
        SpringApplication.run(Application, args)
    }

    @Bean
    Interactor interactor(final AccountRepository accountRepositoryJpa,
                          final TransactionRepository transactionRepositoryJpa,
                          final BankTransaction bankTransaction,
                          final Timer defaultTimer) {
        return new Interactor(accountRepositoryJpa, transactionRepositoryJpa, bankTransaction, defaultTimer)
    }

    @Bean
    BankTransaction bankTransaction(final Timer defaultTimer) {
        return new BankTransaction(defaultTimer)
    }
}
