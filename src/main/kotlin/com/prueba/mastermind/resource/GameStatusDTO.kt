package com.prueba.mastermind.resource

import com.prueba.mastermind.domain.Game
import java.time.format.DateTimeFormatter

data class GameStatusDTO(
    val id: String?=null,
    val secret: String?=null,
    val guesses: List<GuessDTO>?=null,
    val createdAt: String?=null
) {
    companion object {
        fun fromDomain(game: Game) = GameStatusDTO(
            game.id,
            game.getSecret(),
            game.guesses.map { GuessDTO.fromDomain(it) },
            game.createdAt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        )
    }
}
