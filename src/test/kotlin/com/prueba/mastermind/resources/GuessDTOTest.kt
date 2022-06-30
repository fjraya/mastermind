package com.prueba.mastermind.resources

import com.prueba.mastermind.domain.Guess
import com.prueba.mastermind.resource.GuessDTO
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class GuessDTOTest {

    @Test
    fun whenCalledFromDomainConstructsCorrectDTO() {
        val actual = GuessDTO.fromDomain(Guess("RBRB", 3, 1, LocalDateTime.of(2020, 2, 2, 12, 30)))
        Assertions.assertEquals("GuessDTO(combination=RBRB, blackPegs=3, whitePegs=1)", actual.toString())
    }
}