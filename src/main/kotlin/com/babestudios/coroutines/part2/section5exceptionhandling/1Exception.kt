package com.babestudios.coroutines.part2.section5exceptionhandling

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * A very important part of how coroutines behave is their exception handling.
 * Just as a program breaks when an uncaught exception slips by,
 * a coroutine breaks in the case of an uncaught exception.
 * This behavior is nothing new: for instance, threads also end in such cases.
 * The difference is that coroutine builders also cancel their parents,
 * and each cancelled parent cancels all its children.
 * Let’s look at the example below. Once a coroutine receives an exception,
 * it cancels itself and propagates the exception to its parent (launch).
 * The parent cancels itself and all its children, then it propagates the exception to its parent (runBlocking).
 * runBlocking is a root coroutine (it has no parent), so it just ends the program (runBlocking rethrows the exception).
 *
 */
fun main(): Unit = runBlocking {
	launch {
		launch {
			delay(1000)
			throw Error("Some error")
		}
		launch {
			delay(2000)
			println("Will not be printed")
		}
		launch {
			delay(500) // faster than the exception
			println("Will be printed")
		}
	}
	launch {
		delay(2000)
		println("Will not be printed")
	}
}
// Will be printed
// Exception in thread "main" java.lang.Error: Some error...
