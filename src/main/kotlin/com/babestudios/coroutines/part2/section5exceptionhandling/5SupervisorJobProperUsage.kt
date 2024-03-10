package com.babestudios.coroutines.part2.section5exceptionhandling

import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * It would make more sense if we used the same job as a context for multiple coroutine builders because each of them
 * can be cancelled, but they won’t cancel each other.
 */
fun main(): Unit = runBlocking {
	val job = SupervisorJob()
	launch(job) {
		delay(1000)
		throw Error("Some error")
	}
	launch(job) {
		delay(2000)
		println("Will be printed")
	}
	job.join()
}
// (1 sec)
// Exception...
// (1 sec)
// Will be printed
