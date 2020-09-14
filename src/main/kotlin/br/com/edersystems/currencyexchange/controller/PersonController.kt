package br.com.edersystems.currencyexchange.controller

import br.com.edersystems.currencyexchange.controller.request.PersonRequest
import br.com.edersystems.currencyexchange.model.entities.Person
import br.com.edersystems.currencyexchange.model.service.IPersonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/person"], produces = [MediaType.APPLICATION_JSON_VALUE])
class PersonController @Autowired constructor(val service: IPersonService) {

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody request: PersonRequest): ResponseEntity<Person> {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request))
    }
}