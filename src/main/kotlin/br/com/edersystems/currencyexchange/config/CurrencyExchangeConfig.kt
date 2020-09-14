package br.com.edersystems.currencyexchange.config

import org.modelmapper.ModelMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration


@Configuration
@ComponentScan("br.com.edersystems.currencyexchange")
class CurrencyExchangeConfig {

    @Bean
    fun modelMapper(): ModelMapper = ModelMapper()

}