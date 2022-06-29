package com.prueba.mastermind.domain

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class CombinationTest {
    @ParameterizedTest
    @MethodSource("combinationInput")
    fun whenCalledToServiceVerifyThanCombinationHasCorrectLength(size: Int, expected: Int) {
        val actual = Combination.newCombination(size, false)
        Assertions.assertEquals(expected, actual.secretLength())
    }

    @ParameterizedTest
    @MethodSource("combinationInput")
    fun whenCalledToServiceWithNoDuplicatesReturnCombinationWithNonDuplicateColors(size: Int, expected: Int) {
        val actual = Combination.newCombination(size, false)
        Assertions.assertTrue(actual.noDuplicates())
    }

    @ParameterizedTest
    @MethodSource("invalidCombinationInput")
    fun whenCalledToServiceWithInvalidSizeThrowsException(size: Int) {
        Assertions.assertThrows(InvalidCombinationException::class.java) {
            Combination.newCombination(size, false)
        }
    }

    @ParameterizedTest
    @MethodSource("combinationInput")
    fun whenCalledToServiceWithDuplicatesReturnCombinationWithPossibleDuplicateColors(size: Int, expected: Int) {
        val actual = Combination.newCombination(size, true)
        Assertions.assertTrue(actual.secretLength() >= actual.uniqueCharsLength())
    }


    companion object {
        @JvmStatic
        fun combinationInput(): Stream<Arguments> {
            return Stream.of(
                Arguments.arguments(4, 4),
                Arguments.arguments(5, 5),
                Arguments.arguments(6, 6),
            )
        }

        @JvmStatic
        fun invalidCombinationInput(): Stream<Arguments> {
            return Stream.of(
                Arguments.arguments(3),
                Arguments.arguments(7),
                Arguments.arguments(2),
                Arguments.arguments(1),
                Arguments.arguments(0),
                Arguments.arguments(8)
            )
        }
    }


}