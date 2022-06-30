package com.prueba.mastermind.config

import com.prueba.mastermind.application.ActiveGameException
import com.prueba.mastermind.domain.InvalidCombinationException
import com.prueba.mastermind.domain.MaxAttemptsException
import com.prueba.mastermind.resource.GameNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.lang.reflect.UndeclaredThrowableException


data class ErrorInfo(val code: String?, val message: String)

@ControllerAdvice
class GlobalControllerExceptionHandler {

    @ExceptionHandler(ActiveGameException::class)
    fun handleActiveGameException(ex: Exception): ResponseEntity<ErrorInfo> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorInfo("-1", extractMessage(ex) ?: ""))
    }

    @ExceptionHandler(InvalidCombinationException::class)
    fun handleInvalidCombinationException(ex: Exception): ResponseEntity<ErrorInfo> {
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(ErrorInfo("-2", extractMessage(ex) ?: ""))
    }

    @ExceptionHandler(MaxAttemptsException::class)
    fun handleMaxAttemptsException(ex: Exception): ResponseEntity<ErrorInfo> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorInfo("-3", extractMessage(ex) ?: ""))
    }


    @ExceptionHandler(GameNotFoundException::class)
    fun handleGameNotFoundException(ex: Exception): ResponseEntity<ErrorInfo> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorInfo("-4", extractMessage(ex) ?: ""))
    }


    private fun extractMessage(e: Exception): String? {
        return try {
            (e as UndeclaredThrowableException).undeclaredThrowable.message
        }
        catch(ex: Exception) {
            e.message
        }
    }
}