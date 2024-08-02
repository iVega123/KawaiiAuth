package com.auth.kawaii

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KawaiiAuthApplication

fun main(args: Array<String>) {
	runApplication<com.auth.kawaii.KawaiiAuthApplication>(*args)
}

