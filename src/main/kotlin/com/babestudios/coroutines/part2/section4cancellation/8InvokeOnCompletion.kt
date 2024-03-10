package com.babestudios.coroutines.part2.section4cancellation

import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Another mechanism that is often used to free resources is the invokeOnCompletion function from Job.
 * It is used to set a handler to be called when the job reaches a terminal state, namely either
 * “Completed” or “Cancelled”.
 */
suspend fun main(): Unit = coroutineScope {
	val job = launch {
		delay(1000)
	}
	job.invokeOnCompletion { exception: Throwable? ->
		println("Finished ${exception?.message}")
	}
	delay(400)
	job.cancelAndJoin()
}
// Finished StandaloneCoroutine was cancelled
// Finished null, if the second delay is larger than the first