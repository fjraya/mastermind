package com.prueba.mastermind.resources


import com.prueba.mastermind.GameApplication
import com.prueba.mastermind.infrastructure.GameRepository
import com.prueba.mastermind.resource.GameDTO
import com.prueba.mastermind.resource.GameStatusDTO
import com.prueba.mastermind.resource.GuessDTO
import com.prueba.mastermind.resource.GuessInputDTO
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.TestPropertySource

@SpringBootTest(classes = [GameApplication::class], webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
class GameControllerFunctionalTest {
    @LocalServerPort
    private val port = 0
    private val restTemplate = TestRestTemplate()

    @Autowired
    private lateinit var gameRepository: GameRepository

    @BeforeEach
    fun setUp() {
        gameRepository.deleteAll()
    }

    @Test
    fun whenCalledCreateWithNoActiveGamesReturn201() {
        val response = exerciseCreate()
        Assertions.assertEquals(HttpStatus.CREATED, response.statusCode)
    }

    @Test
    fun whenCalledGuessWithActiveReturnCorrectGuess() {
        exerciseCreate()
        val response = exerciseGuess(GuessInputDTO("RGWG"))
        Assertions.assertEquals("200 OK", response.statusCode.toString())
        Assertions.assertTrue(response.body?.blackPegs!! >= 0)
        Assertions.assertTrue(response.body?.whitePegs!! >= 0)
    }


    @Test
    fun whenCalledStatusWithActiveReturnCorrectGameStatus() {
        exerciseCreate()
        (0..8).forEach {
            exerciseGuess(GuessInputDTO("RGWG"))
        }
        val response = exerciseStatus()
        Assertions.assertEquals("200 OK", response.statusCode.toString())
        Assertions.assertEquals(9, response.body?.guesses!!.size)
    }



    private fun exerciseCreate(): ResponseEntity<Unit> {
        val input = GameDTO(4, false)
        val httpEntity = HttpEntity<GameDTO>(input)
        return restTemplate.exchange(
            "http://localhost:$port/api/v1/game/create",
            HttpMethod.POST,
            httpEntity,
            Unit::class.java
        )
    }


    private fun exerciseGuess(input: GuessInputDTO): ResponseEntity<GuessDTO> {
        val httpEntity = HttpEntity<GuessInputDTO>(input)
        return restTemplate.exchange(
            "http://localhost:$port/api/v1/game/guess",
            HttpMethod.POST,
            httpEntity,
            GuessDTO::class.java
        )
    }

    private fun exerciseStatus(): ResponseEntity<GameStatusDTO> {
        return restTemplate.exchange(
            "http://localhost:$port/api/v1/game/status",
            HttpMethod.GET,
            null,
            GameStatusDTO::class.java
        )
    }


}