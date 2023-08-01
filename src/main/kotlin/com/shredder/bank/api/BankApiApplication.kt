package com.shredder.bank.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate

@SpringBootApplication
class BankApiApplication{

	@Bean
	fun restTemplate(builder: RestTemplateBuilder): RestTemplate =
		builder
//			.defaultHeader("X-RapidAPI-Key", "e972a92012msh54c987ab46d9c03p154280jsn08f4400fb39c")
//			.defaultHeader("X-RapidAPI-Host", "jamaican-banks.p.rapidapi.com")
			.build()
}

fun main(args: Array<String>) {
	runApplication<BankApiApplication>(*args)
}
