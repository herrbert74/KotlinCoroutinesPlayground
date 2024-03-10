package com.babestudios.coroutines.part2.section4cancellation

import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Because cancellation happens at the suspension points, it will not happen if there is no suspension point.
 * To simulate such a situation, we could use Thread.sleep instead of delay.
 * This is a terrible practice, so please don’t do this in any real-life projects.
 * We are just trying to simulate a case in which we are using our coroutines extensively but not suspending them.
 * In practice, such a situation might happen if we have some complex calculations,
 * like neural network learning (yes, we also use coroutines for such cases in order to simplify processing
 * parallelization), or when we need to do some blocking calls (for instance, reading files).
 * The example below presents a situation in which a coroutine cannot be cancelled because there is no suspension
 * point inside it (we use Thread.sleep instead of delay).
 * The execution needs over 3 minutes, even though it should be cancelled after 1 second.
 */
suspend fun main(): Unit = coroutineScope {
	val job = Job()
	launch(job) {
		repeat(1_00) { i ->
			Thread.sleep(200) // We might have some complex operations or reading files here println("Printing $i")
		}
	}
	delay(1000)
	job.cancelAndJoin()
	println("Cancelled successfully")
	delay(1000)
}
// Printing 0
// Printing 1
// Printing 2
// ... (up to 1000)