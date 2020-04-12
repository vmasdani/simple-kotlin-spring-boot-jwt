package com.example.jwtstarter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class JwtstarterApplication

fun main(args: Array<String>) {
	runApplication<JwtstarterApplication>(*args)
}
