package com.babestudios.coroutines.part2.section4cancellation

import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield

/**
 * There are a few ways to deal with such situations. The first one is to use the yield() function from time to time.
 * This function suspends and immediately resumes a coroutine.
 * This gives an opportunity to do whatever is needed during suspension (or resuming),
 * including cancellation (or changing a thread using a dispatcher).
 *
 * It is a good practice to use yield in suspend functions,
 * between blocks of non-suspended CPU-intensive or time-intensive operations.
 */
suspend fun main(): Unit = coroutineScope {
	val job = Job()
	launch(job) {
		repeat(1_000) { i ->
			Thread.sleep(200)
			yield()
			println("Printing $i")
		}
	}
	delay(1100)
	job.cancelAndJoin()
	println("Cancelled successfully")
	delay(1000)
}
// Printing 0
// Printing 1
// Printing 2
// Printing 3
// Printing 4
// Cancelled successfully