package com.prueba.mastermind.application

import com.prueba.mastermind.infrastructure.GameRepository
import org.junit.jupiter.api.Test
import com.nhaarman.mockitokotlin2.*
import com.prueba.mastermind.GameMother
import com.prueba.mastermind.domain.InvalidCombinationException
import org.junit.jupiter.api.Assertions
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
        val gameRepositoryStub = mock<GameRepository> {
            on { findByActive(true) } doReturn Optional.of(GameMother.getTestActiveInstance(size, duplication))
        }
        val sut = GameService(gameRepositoryStub)
        Assertions.assertThrows(ActiveGameException::class.java) {
            sut.newGame(size, duplication)
        }
    }

    @Test
    fun whenCalledToNewGameWithNoActiveGamesCorrectCallToSave() {
        val gameRepositoryStub = mock<GameRepository> {
            on { findByActive(true) } doReturn Optional.empty()
        }
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
}