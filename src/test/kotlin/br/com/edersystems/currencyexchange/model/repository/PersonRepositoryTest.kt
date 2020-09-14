package br.com.edersystems.currencyexchange.model.repository

import br.com.edersystems.currencyexchange.controller.request.PersonRequest
import br.com.edersystems.currencyexchange.model.entities.Person
import br.com.edersystems.currencyexchange.model.repository.person.PersonRepository
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class PersonRepositoryTest @Autowired constructor(val repository: PersonRepository) {

    @Test
    @Throws(Exception::class)
    fun `When create Person`() {
        val request = PersonRequest("Test Person", "888.718.867-04")
        val personToBeSaved = Person(request)
        var createdPerson = this.repository.save(personToBeSaved)
        assertNotNull(createdPerson)
        assertNotNull(createdPerson.id)
    }

}