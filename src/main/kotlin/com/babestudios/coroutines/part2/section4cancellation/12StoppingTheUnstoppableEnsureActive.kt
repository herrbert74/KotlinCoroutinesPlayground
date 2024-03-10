package com.babestudios.coroutines.part2.section4cancellation

import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch

/**
 * Alternatively, we might use the ensureActive() function, which throws CancellationException if Job is not active.
 *
 * The result of ensureActive() and yield() seem similar, but they are very different.
 * The function ensureActive() needs to be called on a CoroutineScope (or CoroutineContext, or Job).
 * All it does is throw an exception if the job is no longer active. It is lighter, so generally it should be preferred.
 * The function yield is a regular top-level suspension function.
 * It does not need any scope, so it can be used in regular suspending functions.
 * Since it does suspension and resuming, other effects might arise, such as thread changing
 * if we use a dispatcher with a pool of threads (more about this in the Dispatchers chapter).
 * yield is more often used just in suspending functions that are CPU intensive or are blocking threads.
 */
suspend fun main(): Unit = coroutineScope {
	val job = Job()
	launch(job) {
		repeat(1000) { num ->
			Thread.sleep(200)
			ensureActive()
			println("Printing $num")
		}
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