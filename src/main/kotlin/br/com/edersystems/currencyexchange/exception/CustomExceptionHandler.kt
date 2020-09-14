package br.com.edersystems.currencyexchange.exception

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.core.NestedExceptionUtils
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.TransactionSystemException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.sql.SQLIntegrityConstraintViolationException
import java.util.*
import java.util.stream.Collectors
import javax.validation.ConstraintViolation
import javax.validation.ConstraintViolationException


@ControllerAdvice
class CustomExceptionHandler @Autowired constructor(val messageSource: MessageSource) : ResponseEntityExceptionHandler() {

    protected fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException, status: HttpStatus?): ResponseEntity<Any?> {
        val errorMessage = ex.bindingResult.fieldErrors[0].defaultMessage
        val error = errorMessage?.let { GenericError(HttpStatus.UNPROCESSABLE_ENTITY.value().toString(), it) }
        return ResponseEntity(error, status!!)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handlerException(ex: Exception?): ResponseEntity<GenericError> {
        val errorMessage = messageSource.getMessage(NestedExceptionUtils.getMostSpecificCause(ex!!).localizedMessage, null, Locale.ENGLISH)
        return ResponseEntity(GenericError(HttpStatus.UNPROCESSABLE_ENTITY.value().toString(), errorMessage), HttpStatus.UNPROCESSABLE_ENTITY)
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolation(ex: ConstraintViolationException): ResponseEntity<GenericError> {
        return ResponseEntity(ex.message?.let { GenericError(HttpStatus.BAD_REQUEST.value().toString(), it) }, HttpHeaders(), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleConflict(ex: DataIntegrityViolationException?): ResponseEntity<*>? {
        val message = NestedExceptionUtils.getMostSpecificCause(ex!!).message
        return ResponseEntity<Any>(message?.let { GenericError(HttpStatus.INTERNAL_SERVER_ERROR.value().toString(), it) }, HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handlePersistenceException(ex: DataIntegrityViolationException): ResponseEntity<GenericError> {
        val cause = ex.rootCause
        if (cause is SQLIntegrityConstraintViolationException) {
            val consEx = cause
            return ResponseEntity(GenericError(HttpStatus.INTERNAL_SERVER_ERROR.value().toString(), HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase), HttpStatus.INTERNAL_SERVER_ERROR)
        }
        return ResponseEntity(GenericError(HttpStatus.BAD_REQUEST.value().toString(), HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase), HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(TransactionSystemException::class)
    fun handleConflict(ex: TransactionSystemException): ResponseEntity<*> {
        val cause = ex.rootCause
        var message = NestedExceptionUtils.getMostSpecificCause(ex).localizedMessage
        if (cause is ConstraintViolationException) {
            val constraintViolations = cause.constraintViolations
            message = constraintViolations.stream().map { obj: ConstraintViolation<*> -> obj.message }.collect(Collectors.joining())
            message = messageSource.getMessage(message, null, Locale.ENGLISH)
        }
        return ResponseEntity(GenericError(HttpStatus.UNPROCESSABLE_ENTITY.value().toString(), message!!), HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY)
    }

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFound(ex: NotFoundException?): ResponseEntity<*>? {
        val message = messageSource.getMessage(NestedExceptionUtils.getMostSpecificCause(ex!!).message!!, null, Locale.ENGLISH)
        return ResponseEntity<Any>(GenericError(HttpStatus.NOT_FOUND.value().toString(), message), HttpHeaders(), HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(UnProcessableEntityException::class)
    fun handleUnProcessableEntity(ex: UnProcessableEntityException?): ResponseEntity<*> {
        val message = messageSource.getMessage(NestedExceptionUtils.getMostSpecificCause(ex!!).message!!, null, Locale.ENGLISH)
        return ResponseEntity<Any>(GenericError(HttpStatus.UNPROCESSABLE_ENTITY.value().toString(), message), HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY)
    }
}