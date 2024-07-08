package com.babestudios.coroutines.part2.section5exceptionhandling


import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * THIS IS NOT IN THE BOOK!!!
 * CoroutineExceptionHandler DOES stop propagating exceptions in a parent coroutine.
 * If we move the handler to the inner launch, the exception propagates up to the parent as soon as it happens, since
 * the parent doesn't know anything about the handler, the exception will be thrown.
 */
fun main(): Unit = runBlocking {
	val handler =
		CoroutineExceptionHandler { _, exception ->
			println("Caught $exception")
		}
	val scope = CoroutineScope(Job())
	scope.launch(handler) {
		launch {
			throw Exception("Failed coroutine")
		}

	}
	delay(100)
	println("Run")

}
