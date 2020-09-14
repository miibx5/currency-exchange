package br.com.edersystems.currencyexchange.model.service

import br.com.edersystems.currencyexchange.controller.request.PersonRequest
import br.com.edersystems.currencyexchange.model.entities.Person
import br.com.edersystems.currencyexchange.model.repository.person.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class IPersonService @Autowired constructor(val repository: PersonRepository) {

    fun create(request: PersonRequest): Person {
        return repository.save(Person(request))
    }
}