package com.prueba.mastermind.config

import com.prueba.mastermind.application.ActiveGameException
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

    private fun extractMessage(e: Exception): String? {
        return try {
            (e as UndeclaredThrowableException).undeclaredThrowable.message
        }
        catch(ex: Exception) {
            e.message
        }
    }
}