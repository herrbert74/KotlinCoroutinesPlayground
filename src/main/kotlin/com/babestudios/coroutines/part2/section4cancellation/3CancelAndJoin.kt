package com.babestudios.coroutines.part2.section4cancellation

import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * To make it easier to call cancel and join together, the kotlinx.coroutines library offers a convenient extension
 * function with a self-descriptive name, cancelAndJoin.
 * A job created using the Job() factory function can be cancelled in the same way.
 * This is often used to make it easy to cancel many coroutines at once.
 */
suspend fun main(): Unit = coroutineScope {
	val job = Job()
	launch(job) {
		repeat(1_000) { i ->
			delay(200)
			println("Printing $i") }
	}
	delay(1100)
	job.cancelAndJoin()
	println("Cancelled successfully")
}
// Printing 0
// Printing 1
// Printing 2
// Printing 3
// Printing 4
// Cancelled successfully