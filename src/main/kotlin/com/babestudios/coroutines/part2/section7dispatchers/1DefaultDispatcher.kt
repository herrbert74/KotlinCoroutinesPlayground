package com.babestudios.coroutines.part2.section7dispatchers

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.random.Random

/**
 * If you don’t set any dispatcher, the one chosen by default is Dispatchers.Default,
 * which is designed to run CPU-intensive operations.
 * It has a pool of threads with a size equal to the number of cores in the machine your code is running on (but not
 * less than two). At least theoretically, this is the optimal number of threads, assuming you are using these threads
 * efficiently, i.e., performing CPU-intensive calculations and not blocking them.
 * To see this dispatcher in action, run the following code:
 */
suspend fun main() = coroutineScope {
	repeat(1000) {
		launch { // or launch(Dispatchers.Default) {
			// To make it busy
			List(1000) { Random.nextLong() }.maxOrNull()
			val threadName = Thread.currentThread().name
			println("Running on thread: $threadName")
		}
	}
}
