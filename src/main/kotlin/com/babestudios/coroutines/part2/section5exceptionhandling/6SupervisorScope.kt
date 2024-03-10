package com.babestudios.coroutines.part2.section5exceptionhandling

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope

/**
 * Another way to stop exception propagation is to wrap coroutine builders with supervisorScope.
 * This is very convenient as we still keep a connection to the parent,
 * yet any exceptions from the coroutine will be silenced.
 */
fun main(): Unit = runBlocking {
	supervisorScope {
		launch {
			delay(1000)
			throw Error("Some error")
		}
		launch {
			delay(2000)
			println("Will be printed")
		}
	}
	delay(1000)
	println("Done")
}
// Exception...
// Will be printed
// (1 sec)
// Done
