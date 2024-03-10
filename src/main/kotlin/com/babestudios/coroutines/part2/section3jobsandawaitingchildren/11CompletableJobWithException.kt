package com.babestudios.coroutines.part2.section3jobsandawaitingchildren

import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Continued from previous.
 *
 * completeExceptionally(exception: Throwable): Boolean - Completes this job with a given exception.
 * This means that all children will be cancelled immediately (with CancellationException wrapping the exception
 * provided as an argument).
 * The result, just like in the above function, responds to the question:
 * “Was this job finished because of the invocation?”.
 */
fun main() = runBlocking { val job = Job()
	launch(job) {
		repeat(5) { num ->
			delay(200)
			println("Rep$num") }
	}
	launch {
		delay(500)
		job.completeExceptionally(Error("Some error"))
	}
	job.join()
	launch(job) {
		println("Will not be printed")
	}
	println("Done")
}
// Rep0
// Rep1
// Done
