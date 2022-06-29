package com.prueba.mastermind.domain

import com.prueba.mastermind.GameMother
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.*
import java.util.stream.Stream


class GameTest {
    @ParameterizedTest
    @MethodSource("combinationInput")
    fun whenCalledToCalculatePegsReturnCorrectValues(
        secret: String,
        combination: String,
        blackPegs: Int,
        whitePegs: Int
    ) {
        val game = Game(UUID.randomUUID().toString(), 4, false, Combination(secret))
        val guess = game.calculatePegs(combination)
        Assertions.assertEquals(blackPegs, guess.blackPegs)
        Assertions.assertEquals(whitePegs, guess.whitePegs)
    }


    @Test
    fun whenCalledToAddGuestWith10GuessesRefuseAdd() {
        val game = GameMother.getTestActiveInstance(4, false)
        game.addGuess(game.calculatePegs("ABWO"))
        game.addGuess(game.calculatePegs("ABWO"))
        game.addGuess(game.calculatePegs("ABWO"))
        game.addGuess(game.calculatePegs("ABWO"))
        game.addGuess(game.calculatePegs("ABWO"))
        game.addGuess(game.calculatePegs("ABWO"))
        game.addGuess(game.calculatePegs("ABWO"))
        game.addGuess(game.calculatePegs("ABWO"))
        game.addGuess(game.calculatePegs("ABWO"))
        game.addGuess(game.calculatePegs("ABWO"))
        game.addGuess(game.calculatePegs("ABWO"))
        game.addGuess(game.calculatePegs("ABWO"))
        game.addGuess(game.calculatePegs("ABWO"))
        val expected = 10
        Assertions.assertEquals(expected, game.countGuesses())
    }


    companion object {
        @JvmStatic
        fun combinationInput(): Stream<Arguments> {
            return Stream.of(
                Arguments.arguments("RGGB", "RGGB", 4, 0),
                Arguments.arguments("RRRR", "BYOB", 0, 0),
                Arguments.arguments("GBBR", "GBRB", 2, 2),
                Arguments.arguments("BBBR", "RBGG", 1, 1),
                Arguments.arguments("RBGG", "BBBR", 1, 1),
                Arguments.arguments("BBBR", "BBBR", 4, 0),
                Arguments.arguments("WBWB", "BWBW", 0, 4),
                Arguments.arguments("OOOW", "OWWW", 2, 0),

                )
        }


    }
}