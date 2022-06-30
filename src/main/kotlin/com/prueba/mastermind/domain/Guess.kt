package com.prueba.mastermind.domain

import java.time.LocalDateTime
import javax.persistence.Embeddable

@Embeddable
data class Guess(val combination: String, val blackPegs: Int, val whitePegs: Int, val createdAt: LocalDateTime = LocalDateTime.now()) {
    fun solved() = this.blackPegs == 4

}