package com.babestudios.coroutines.part2.section4cancellation

import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

/**
 * When a job is cancelled, it changes its state to “Cancelling”.
 * Then, at the first suspension point, a CancellationException is thrown.
 * This exception can be caught using a try-catch, but it is recommended to rethrow it.
 */
suspend fun main(): Unit = coroutineScope {
	val job = Job()
	launch(job) {
		try {
			repeat(1_000) { i ->
				delay(200)
				println("Printing $i")
			}
		} catch (e: CancellationException) {
			println(e)
			throw e
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
// JobCancellationException...
// Cancelled successfully