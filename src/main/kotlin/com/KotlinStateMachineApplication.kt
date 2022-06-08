package com

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinStateMachineApplication

fun main(args: Array<String>) {
    runApplication<KotlinStateMachineApplication>(*args)
}
