package mvi.finance.referential.util

import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest


class ReferentialRuntimeException(message: String,
                                  val origin: String = "Referential Service",
                                  val scope: String = "INTERNAL") : RuntimeException(message) {}

data class ErrorDto(val message: String,
                    val origin: String,
                    val scope: String)

@RestControllerAdvice
class ReferentialRestErrorAdvice {

    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun validationError(exception: MethodArgumentNotValidException,
                        req: WebRequest): ErrorDto = ErrorDto(exception.message,
                                                              "Referential Rest Service",
                                                              "INPUT_VALIDATION")

    @ExceptionHandler(value = [MissingKotlinParameterException::class, HttpMessageNotReadableException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun validationErrorKotlin(exception: Exception,
                        req: WebRequest): ErrorDto = ErrorDto(exception.localizedMessage,
                                                              "Referential Rest Service",
                                                              "INPUT_VALIDATION")


    @ExceptionHandler(value = [NoSuchElementException::class])
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun notFoundError(exception: NoSuchElementException,
                      req: WebRequest): ErrorDto = ErrorDto(exception.message ?: "Element Not Found",
                                                            "Referential Rest Service",
                                                            "ELEMENT_NOT_FOUND")

    @ExceptionHandler(value = [ReferentialRuntimeException::class])
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun internalError(ex: ReferentialRuntimeException,
                      req: WebRequest): ErrorDto = ErrorDto(ex.message ?: "Internal Error",
                                                            ex.origin,
                                                            ex.scope)
}