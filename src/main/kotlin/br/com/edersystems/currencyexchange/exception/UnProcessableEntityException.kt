package br.com.edersystems.currencyexchange.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
class UnProcessableEntityException(message: String) : RuntimeException(message) {
}