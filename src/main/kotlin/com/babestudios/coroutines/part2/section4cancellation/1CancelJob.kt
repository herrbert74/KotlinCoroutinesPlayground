package com.babestudios.coroutines.part2.section4cancellation

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * We might cancel with a different exception (by passing an exception as an argument to the cancel function)
 * to specify the cause.
 * This cause needs to be a subtype of CancellationException, because only an Exception of this type can be used
 * to cancel a coroutine.
 */
suspend fun main(): Unit = coroutineScope {
	val job = launch {
		repeat(1_000) { i ->
			delay(200)
			println("Printing $i")
		}
	}
	delay(1100)
	job.cancel()
	job.join()
	println("Cancelled successfully")
}
// Printing 0
// Printing 1
// Printing 2
// Printing 3
// Printing 4
// Cancelled successfully