package com.prueba.mastermind.application

import com.nhaarman.mockitokotlin2.*
import com.prueba.mastermind.GameMother
import com.prueba.mastermind.domain.Game
import com.prueba.mastermind.domain.Guess
import com.prueba.mastermind.domain.MaxAttemptsException
import com.prueba.mastermind.infrastructure.GameRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.*


class GameServiceTest {
    val size = 4
    val duplication = false

    @Test
    fun whenCalledToNewGameCorrectCallToInnerGameRepositoryFindByActive() {
        val gameRepositoryMock = mock<GameRepository> {}
        val sut = GameService(gameRepositoryMock)
        sut.newGame(size, duplication)
        verify(gameRepositoryMock, times(1)).findByActive(true)
    }

    @Test
    fun whenCalledToNewGameAndNewGameExistsThrowsException() {
        val gameRepositoryStub = configureGameRepositoryStubWithActiveGame()
        val sut = GameService(gameRepositoryStub)
        Assertions.assertThrows(ActiveGameException::class.java) {
            sut.newGame(size, duplication)
        }
    }



    @Test
    fun whenCalledToNewGameWithNoActiveGamesCorrectCallToSave() {
        val gameRepositoryStub = configureGameRepositoryStubWithNoActiveGame()
        val sut = GameService(gameRepositoryStub)
        sut.newGame(size, duplication)
        verify(gameRepositoryStub, times(1)).save(any())
    }



    @Test
    fun whenCalledToEndGameCorrectCallToInnerRepository() {
        val gameRepositoryMock = mock<GameRepository> {}
        val sut = GameService(gameRepositoryMock)
        sut.endGame()
        verify(gameRepositoryMock, times(1)).endGame()
    }




    @Test
    fun whenCalledToGuessWithNoActiveGameThrowsException() {
        val gameRepositoryStub = configureGameRepositoryStubWithNoActiveGame()
        val sut = GameService(gameRepositoryStub)
        Assertions.assertThrows(ActiveGameException::class.java) {
            sut.guess("WAWA")
        }
    }

    @Test
    fun whenCalledToGuessWithActiveGameAndLessThan11AttemptsCorrectCallToSave() {
        val gameRepositoryMock = configureGameRepositoryStubWithActiveGame()
        val sut = GameService(gameRepositoryMock)
        sut.guess("RBRB")
        argumentCaptor<Game>().apply {
            verify(gameRepositoryMock, times(1)).save(capture())
            Assertions.assertEquals(1, firstValue.countGuesses())
        }
    }





    @Test
    fun whenCalledToGuessWithActiveGameAndExactly11AttemptsThrows() {
        val gameRepositoryStub = configureGameRepositoryStubWithActiveGame()
        val sut = GameService(gameRepositoryStub)
        add10Guests(sut)
        Assertions.assertThrows(MaxAttemptsException::class.java) {
            sut.guess("RBRB")
        }
    }


    @Test
    fun whenCalledToGuessWithActiveGameAndExactly10AttemptsCallToEndGame() {
        val gameRepositoryStub = configureGameRepositoryStubWithActiveGame()
        val sut = GameService(gameRepositoryStub)
        add10Guests(sut)
        verify(gameRepositoryStub, times(1)).endGame()
    }


    @Test
    fun whenCalledToGuessWithActiveGameAndSolvesTheProblemCallToEndGame() {
        val gameRepositoryStub = configureGameRepositoryStubWithActiveGame()
        val sut = GameService(gameRepositoryStub)
        sut.guess("ABAB")
        verify(gameRepositoryStub, times(1)).endGame()
    }

    private fun configureGameRepositoryStubWithActiveGame(): GameRepository {
        val gameRepositoryStub = mock<GameRepository> {
            on { findByActive(true) } doReturn Optional.of(GameMother.getTestActiveInstance(size, duplication))
        }
        return gameRepositoryStub
    }

    private fun configureGameRepositoryStubWithNoActiveGame(): GameRepository {
        val gameRepositoryStub = mock<GameRepository> {
            on { findByActive(true) } doReturn Optional.empty()
        }
        return gameRepositoryStub
    }

    private fun add10Guests(sut: GameService) {
        sut.guess("RBRB")
        sut.guess("RBRB")
        sut.guess("RBRB")
        sut.guess("RBRB")
        sut.guess("RBRB")
        sut.guess("RBRB")
        sut.guess("RBRB")
        sut.guess("RBRB")
        sut.guess("RBRB")
        sut.guess("RBRB")
    }

}