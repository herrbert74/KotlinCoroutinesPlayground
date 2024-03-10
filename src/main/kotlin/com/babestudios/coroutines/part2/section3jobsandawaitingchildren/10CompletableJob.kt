package com.babestudios.coroutines.part2.section3jobsandawaitingchildren

import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * The CompletableJob interface extends the functionality of the Job
 * interface by providing two additional methods:
 *
 * complete(): Boolean - used to complete a job. Once it is used, all the child coroutines will keep running until
 * they are all done, but new coroutines cannot be started in this job.
 * The result is true if this job was completed as a result of this invocation;
 * otherwise, it is false (if it was already completed).
 *
 * completeExceptionally(exception: Throwable): See next
 */
fun main() = runBlocking {
	val job = Job()
	launch(job) {
		repeat(5) { num ->
			delay(200)
			println("Rep$num") }
	}
	launch {
		delay(500)
		job.complete()
	}
	job.join()
	launch(job) {
		println("Will not be printed")
	}
	println("Done")
}
// Rep0
// Rep1
// Rep2
// Rep3
// Rep4
// Done