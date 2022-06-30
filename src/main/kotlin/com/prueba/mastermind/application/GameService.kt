package com.prueba.mastermind.application

import com.prueba.mastermind.domain.Game
import com.prueba.mastermind.domain.Guess
import com.prueba.mastermind.infrastructure.GameRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class GameService(private val gameRepository: GameRepository) {
    fun newGame(size: Int, duplication: Boolean) {
        val game = gameRepository.findByActive(true)
        if (game.isPresent) throw ActiveGameException("There is an active game")
        gameRepository.save(Game(UUID.randomUUID().toString(), size, duplication))
    }

    fun endGame() {
        gameRepository.endGame()
    }


    fun guess(combination: String): Guess {
        val game = getActiveGame()
        if (!game.isPresent) throw ActiveGameException("There isn't an active game.")
        val innerGame = game.get()
        val guess = innerGame.addGuess(combination)
        gameRepository.save(innerGame)
        if (!innerGame.canAddGuess() || guess.solved()) gameRepository.endGame()
        return guess
    }


    fun getActiveGame() = gameRepository.findByActive(true)
}