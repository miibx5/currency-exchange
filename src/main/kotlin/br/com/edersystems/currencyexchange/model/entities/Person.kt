package br.com.edersystems.currencyexchange.model.entities

import br.com.edersystems.currencyexchange.controller.request.PersonRequest
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "people")
data class Person(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long?,

        @NotBlank(message = "person.name.required")
        @Column(length = 120, nullable = false)
        val name: String,

        @NotBlank(message = "person.cpf.required")
        @Column(length = 14, nullable = false, unique = true)
        val cpf: String
) {
    constructor(request: PersonRequest) : this(null, request.name, request.cpf)
}