package com.prueba.mastermind

import com.prueba.mastermind.domain.Game
import java.util.*

class GameMother {
    companion object {
        fun getTestActiveInstance(size: Int, duplication: Boolean) = Game(UUID.randomUUID().toString(), size, duplication, true)
        fun getTestNoActiveInstance(size: Int, duplication: Boolean) = Game(UUID.randomUUID().toString(), size, duplication, false)
    }
}