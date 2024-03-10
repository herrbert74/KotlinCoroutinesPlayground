package com.babestudios.coroutines.part2.section5exceptionhandling


import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * When dealing with exceptions, sometimes it is useful to define default behavior for all of them.
 * This is where the CoroutineExceptionHandler context comes in handy.
 * It does not stop the exception propagating, but it can be used to define what should happen in the case of an
 * exception (by default, it prints the exception stack trace).
 */
fun main(): Unit = runBlocking {
	val handler =
		CoroutineExceptionHandler { _, exception ->
			println("Caught $exception")
		}
	val scope = CoroutineScope(SupervisorJob() + handler)
	scope.launch {
		delay(1000)
		throw Error("Some error")
	}
	scope.launch {
		delay(2000)
		println("Will be printed")
	}
	delay(3000)
}
// Caught java.lang.Error: Some error
// Will be printed
