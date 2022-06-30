package com.prueba.mastermind.resource

import com.prueba.mastermind.domain.Guess

data class GuessDTO(val combination: String?=null, val blackPegs: Int?=null, val whitePegs: Int?=null) {
    companion object {
        fun fromDomain(guess: Guess) = GuessDTO(guess.combination, guess.blackPegs, guess.whitePegs)
    }
}