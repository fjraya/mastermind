package com.prueba.mastermind.infrastructure

import com.prueba.mastermind.GameApplication
import com.prueba.mastermind.GameMother
import com.prueba.mastermind.domain.Game
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@SpringBootTest(classes = [(GameApplication::class)])
@ExtendWith(SpringExtension::class)
@TestPropertySource("classpath:test.properties")
class GameRepositoryIntegrationTest {
    @Autowired
    private lateinit var gameRepository: GameRepository

    @BeforeEach
    fun setUp() {
        tearDown()
    }

    private fun setupInitialData() = gameRepository.save(GameMother.getTestActiveInstance(4, false))


    @AfterEach
    fun tearDown() {
        gameRepository.deleteAll()
    }

    @Test
    fun whenCalledSaveCorrectInsertion() {
        val (_, game) = exerciseSaveAndFind()
        Assertions.assertEquals(true, game.get().isActive())
    }



    @Test
    fun whenCalledSaveCorrectSecretInsertion() {
        val (firstGame, game) = exerciseSaveAndFind()
        Assertions.assertEquals(firstGame.getSecret(), game.get().getSecret())
    }


    @Test
    fun whenCallToFindByActiveWithActiveGamesReturnCorrectResult() {
        gameRepository.save(GameMother.getTestNoActiveInstance(4, false))
        gameRepository.save(GameMother.getTestActiveInstance(5, false))
        gameRepository.save(GameMother.getTestNoActiveInstance(4, false))
        val game = gameRepository.findByActive(true)
        Assertions.assertEquals(5, game.get().getSecret().chars().count())
    }


    private fun exerciseSaveAndFind(): Pair<Game, Optional<Game>> {
        val guard = gameRepository.count()
        Assertions.assertEquals(0, guard, "table games must be empty")
        val firstGame = setupInitialData()
        val game = gameRepository.findById(firstGame.id)
        return Pair(firstGame, game)
    }



}