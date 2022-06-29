package com.prueba.mastermind.infrastructure

import com.prueba.mastermind.domain.Game
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface GameRepository : JpaRepository<Game, String> {
    fun findByActive(active:Boolean): Optional<Game>
}