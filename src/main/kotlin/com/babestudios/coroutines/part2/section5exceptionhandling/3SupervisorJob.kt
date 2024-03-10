package com.babestudios.coroutines.part2.section5exceptionhandling

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * The most important way to stop coroutines breaking is by using a SupervisorJob.
 * This is a special kind of job that ignores all exceptions in its children.
 *
 * SupervisorJob is generally used as part of a scope in which we start multiple coroutines
 * (more about this in the Constructing coroutine scope chapter).
 */
fun main(): Unit = runBlocking {
	val scope = CoroutineScope(SupervisorJob())
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
// Exception...
// Will be printed
