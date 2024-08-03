package com.auth.kawaii

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.SecurityScheme
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SecurityScheme(
	name = "bearerAuth",
	scheme = "bearer",
	bearerFormat = "JWT",
	type = SecuritySchemeType.HTTP
)
@SpringBootApplication
class KawaiiAuthApplication

fun main(args: Array<String>) {
	runApplication<KawaiiAuthApplication>(*args)
}

