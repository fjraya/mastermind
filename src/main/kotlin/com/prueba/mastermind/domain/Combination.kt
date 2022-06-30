package com.prueba.mastermind.domain

import javax.persistence.Embeddable

@Embeddable
class Combination (val secret: String) {

    fun secretLength() = secret.length

    fun noDuplicates() = secretLength().toLong() == uniqueCharsLength()

    fun uniqueCharsLength() = secret.chars().distinct().count()

    companion object {
        const val MIN_SIZE = 4
        const val MAX_SIZE= 6
        fun newCombination(size: Int, duplicates: Boolean): Combination {
            if ((size < MIN_SIZE) || (size > MAX_SIZE)) throw InvalidCombinationException("Invalid size: $size")
            val colors = arrayOf("R", "G", "B", "O", "Y", "W")
            colors.shuffle()
            if (!duplicates) return Combination(colors.copyOfRange(0, size).joinToString(""))
            //Case of duplicates
            var result = ""
            for (i in 0 until size) {
                result += colors[(colors.indices).shuffled()[0]]
            }
            return Combination(result)
        }
    }
}