package com.prueba.mastermind.domain

import java.time.LocalDate
import javax.persistence.Embeddable
@Embeddable
data class Guess(val combination: String, val blackPegs: Int, val whitePegs: Int, val createdAt: LocalDate = LocalDate.now())