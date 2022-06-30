package com.prueba.mastermind.resource

import com.prueba.mastermind.application.GameService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/api/v1"])
class GameController(private val gameService: GameService) {
    @PostMapping("/game/create")
    fun createGame(@RequestBody game: GameDTO): ResponseEntity<Unit> {
        gameService.newGame(game.size, game.duplication)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @DeleteMapping("/game/end")
    fun endGame(): ResponseEntity<Unit> {
        gameService.endGame()
        return ResponseEntity.status(HttpStatus.OK).build()
    }

    @GetMapping("/game/status")
    fun getGameStatus(): ResponseEntity<GameStatusDTO> {
        val game = gameService.getActiveGame().orElseThrow { GameNotFoundException() }
        return ResponseEntity.ok(GameStatusDTO.fromDomain(game))
    }

    @PostMapping("/game/guess")
    fun guess(@RequestBody input: GuessInputDTO): ResponseEntity<GuessDTO> {
        val guess = gameService.guess(input.combination!!)
        return ResponseEntity.ok(GuessDTO.fromDomain(guess))
    }


}