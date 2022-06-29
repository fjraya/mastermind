package com.prueba.mastermind.resource

import com.prueba.mastermind.application.GameService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api/v1"])
class GameController(private val gameService: GameService) {
    @PostMapping("/game/create")
    fun createGame(@RequestBody game: GameDTO): ResponseEntity<Unit> {
        gameService.newGame(game.size, game.duplication)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }


}