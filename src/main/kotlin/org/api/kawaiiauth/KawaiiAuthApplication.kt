package org.api.kawaiiauth

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KawaiiAuthApplication

fun main(args: Array<String>) {
	runApplication<KawaiiAuthApplication>(*args)
}
