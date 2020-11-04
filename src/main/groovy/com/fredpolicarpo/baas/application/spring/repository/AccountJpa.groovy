package com.fredpolicarpo.baas.application.spring.repository

import groovy.transform.Canonical
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.Version
import java.time.Instant

@Entity
@Canonical
@Table(name = "accounts")
class AccountJpa implements Serializable {

    // Business Data
    @Column
    String documentNumber

    @Column
    BigDecimal creditLimit = new BigDecimal("0.00")

    // DB Stuff

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @Version
    long version

    @CreationTimestamp
    Instant createdAt

    @UpdateTimestamp
    Instant updatedAt

    Instant deletedAt
}
