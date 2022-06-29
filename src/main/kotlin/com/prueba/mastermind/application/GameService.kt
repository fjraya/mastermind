package com.prueba.mastermind.application

import com.prueba.mastermind.domain.Game
import com.prueba.mastermind.infrastructure.GameRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class GameService(private val gameRepository: GameRepository) {
    fun newGame(size: Int, duplication: Boolean) {

        

    }
}