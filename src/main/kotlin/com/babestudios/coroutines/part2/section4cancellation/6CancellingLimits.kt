package com.babestudios.coroutines.part2.section4cancellation

import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Since we can catch CancellationException and invoke more operations before the coroutine truly ends,
 * you might be wondering where the limit is.
 * The coroutine can run as long as it needs to clean up all the resources.
 * However, suspension is no longer allowed. The Job is already in a “Cancelling” state,
 * in which suspension or starting another coroutine is not possible at all.
 * If we try to start another coroutine, it will just be ignored.
 * If we try to suspend, it will throw CancellationException.
 */
suspend fun main(): Unit = coroutineScope {
	val job = Job()
	launch(job) {
		try {
			delay(2000)
			println("Job is done")
		} finally {
			println("Finally")
			launch { // will be ignored
				println("Will not be printed")
			}
			delay(1000) // here exception is thrown
			println("Will not be printed")
		}
	}
	delay(1000)
	job.cancelAndJoin()
	println("Cancel done")
}
// (1 sec)
// Finally
// Cancel done