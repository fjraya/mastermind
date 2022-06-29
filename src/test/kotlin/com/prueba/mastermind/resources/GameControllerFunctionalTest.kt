package com.prueba.mastermind.resources


import com.prueba.mastermind.GameApplication
import com.prueba.mastermind.infrastructure.GameRepository
import com.prueba.mastermind.resource.GameDTO
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
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
import java.util.stream.Stream

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
    fun whenCalledWithNoActiveGamesReturn201() {
        val input = GameDTO(4, false)
        val response = exerciseCreate(input)
        Assertions.assertEquals(HttpStatus.CREATED, response.statusCode)
    }



    private fun exerciseCreate(input: GameDTO): ResponseEntity<Unit> {
        val httpEntity = HttpEntity<GameDTO>(input)
        return restTemplate.exchange(
            "http://localhost:$port/api/v1/game/create",
            HttpMethod.POST,
            httpEntity,
            Unit::class.java
        )
    }


}