package com.babestudios.coroutines.part2.section5exceptionhandling

import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * A common mistake is to use a SupervisorJob as an argument to a parent coroutine, like in the code below.
 * It won’t help us handle exceptions, because in such a case SupervisorJob has only one direct child,
 * namely the launch defined at 1 that received this SupervisorJob as an argument.
 * So, in such a case there is no advantage of using SupervisorJob over Job
 * (in both cases, the exception will not propagate to runBlocking because we are not using its job).
 */
fun main(): Unit = runBlocking {
	// Don't do that, SupervisorJob with one child and no parent, works similar to just Job
	launch(SupervisorJob()) { //1
		launch {
			delay(1000)
			throw Error("Some error")
		}
		launch {
			delay(2000)
			println("Will not be printed")

		}
	}
	delay(3000)
}
// Exception...
