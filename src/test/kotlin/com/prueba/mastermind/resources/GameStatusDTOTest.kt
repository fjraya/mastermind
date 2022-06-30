package com.prueba.mastermind.resources

import com.prueba.mastermind.GameMother
import com.prueba.mastermind.resource.GameStatusDTO
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class GameStatusDTOTest {

    @Test
    fun whenCalledFromDomainConstructsCorrectDTO() {
        val actual = exerciseFromDomain()
        Assertions.assertEquals(2, actual.guesses!!.size)
    }

    @Test
    fun whenCalledFromDomainConstructsCorrectCombinations() {
        val actual = exerciseFromDomain()
        Assertions.assertEquals(2, actual.guesses!!.filter { it.combination == "RBRB" || it.combination == "ABRB" }.size)
    }

    private fun exerciseFromDomain(): GameStatusDTO {
        val game = GameMother.getTestActiveInstance(4, false)
        game.addGuess("RBRB")
        game.addGuess("ABRB")
        val actual = GameStatusDTO.fromDomain(game)
        return actual
    }
}