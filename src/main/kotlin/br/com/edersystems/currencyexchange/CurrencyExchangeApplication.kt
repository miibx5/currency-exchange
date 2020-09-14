package br.com.edersystems.currencyexchange

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CurrencyExchangeApplication

fun main(args: Array<String>) {
	runApplication<CurrencyExchangeApplication>(*args)
}
