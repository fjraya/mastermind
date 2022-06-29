package com.prueba.mastermind.domain

import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "games")
class Game(
    @Id val id: String, size: Int, duplication: Boolean, @Embedded val secret: Combination = Combination.newCombination(size, duplication), private val active: Boolean = true,
    @ElementCollection(fetch = FetchType.EAGER)
    val guesses: MutableSet<Guess> = mutableSetOf(), val createdAt: LocalDate = LocalDate.now()
) {

    companion object {
        const val MAX_ATTEMPTS = 10
    }

    fun isActive() = active

    fun getSecret() = secret.secret

    fun canAddGuess() = this.guesses.size < MAX_ATTEMPTS

    fun addGuess(guess: Guess) {
        if (canAddGuess()) this.guesses.add(guess)
    }

    fun calculatePegs(combination: String): Guess {
        val secretLength = getSecret().length
        val combinationLength = combination.length
        if (secretLength != combination.length) throw InvalidCombinationException("Secret size: $secretLength and your combination size:  $combinationLength")
        val secretArray = getSecret().toCharArray().toMutableList()
        val combinationArray = combination.toCharArray().toList()

        var blackPegs = 0
        for ((i, _) in secretArray.withIndex()) {
            if (secretArray[i] == combinationArray[i]) blackPegs++
        }
        for (elem in combinationArray) {
            secretArray.remove(elem)
        }

        return Guess(combination, blackPegs, (combination.length - secretArray.size) - blackPegs)
    }

    fun countGuesses() = this.guesses.size

}