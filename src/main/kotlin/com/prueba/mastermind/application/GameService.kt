package com.prueba.mastermind.application

import com.prueba.mastermind.domain.Combination
import org.springframework.stereotype.Service

@Service
class GameService {
    fun newGame(size: Int, duplicates: Boolean) {
        val combination = Combination.newCombination(size, duplicates)

    }
}