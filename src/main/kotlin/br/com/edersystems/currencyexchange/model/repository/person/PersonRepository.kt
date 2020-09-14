package br.com.edersystems.currencyexchange.model.repository.person

import br.com.edersystems.currencyexchange.model.entities.Person
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PersonRepository : JpaRepository<Person, Long> {
}