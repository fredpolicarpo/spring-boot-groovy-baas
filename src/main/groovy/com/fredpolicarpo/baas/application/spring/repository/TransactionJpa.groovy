package com.fredpolicarpo.baas.application.spring.repository

import com.fredpolicarpo.baas.business.entities.OperationType
import org.hibernate.annotations.CreationTimestamp

import javax.persistence.*
import java.time.Instant

@Entity
@Table(name = "transactions")
class TransactionJpa implements Serializable {

    // Business Data
    @ManyToOne
    AccountJpa account

    OperationType operationType

    @Column
    BigDecimal amount

    @Column
    Instant eventDate


    // DB Stuff
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @CreationTimestamp
    Instant createdAt
}
