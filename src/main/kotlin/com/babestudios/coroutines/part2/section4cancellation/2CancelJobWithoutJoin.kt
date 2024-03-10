package com.babestudios.coroutines.part2.section4cancellation

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * After cancel, we often also add join to wait for the cancellation to finish before we can proceed.
 * Without this, we would have some race conditions.
 * The snippet below shows an example in which without join we will see “Printing 4” after “Cancelled successfully”.
 */
suspend fun main(): Unit = coroutineScope {
	val job = launch {
		repeat(1_000) { i ->
			delay(100)
			Thread.sleep(100) // We simulate long operation
			println("Printing $i") }
	}
	delay(1000)
	job.cancel()
	println("Cancelled successfully")
}
// Printing 0
// Printing 1
// Printing 2
// Printing 3
// Cancelled successfully
// Printing 4