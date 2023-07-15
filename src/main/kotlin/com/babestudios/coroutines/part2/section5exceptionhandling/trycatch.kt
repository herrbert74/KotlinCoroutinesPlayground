package com.babestudios.coroutines.part2.section5exceptionhandling

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {
// Don't wrap in a try-catch here. It will be ignored.
	try {
		launch {
			delay(1000)
			throw Exception("Some error")
		}
	} catch (e: Throwable) { // nope, does not help here println("Will not be printed")
	}
	launch {
		delay(2000)
		println("Will not be printed")
	}
}
