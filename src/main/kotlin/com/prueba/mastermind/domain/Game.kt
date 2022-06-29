package com.prueba.mastermind.domain

import java.time.LocalDate
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "games")
class Game(@Id val id: String, size: Int, duplication: Boolean, private val active: Boolean = true, val createdAt: LocalDate = LocalDate.now()) {
    @Embedded private val secret: Combination
    init {
        secret = Combination.newCombination(size, duplication)
    }

    fun isActive() = active

    fun getSecret() = secret.secret

}