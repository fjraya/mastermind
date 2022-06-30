package com.prueba.mastermind.infrastructure

import com.prueba.mastermind.domain.Game
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*


@Repository
interface GameRepository : JpaRepository<Game, String> {
    fun findByActive(active:Boolean): Optional<Game>

    @Transactional
    @Modifying
    @Query("update Game g set g.active = false where g.active = true")
    fun endGame()
}