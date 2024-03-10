package com.babestudios.coroutines.part2.section4cancellation

import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

/**
 * Keep in mind that a cancelled coroutine is not just stopped: it is cancelled internally using an exception.
 * Therefore, we can freely clean up everything inside the finally block.
 * For instance, we can use a finally block to close a file or a database connection.
 * Since most resource-closing mechanisms rely on the finally block (for instance, if we read a file using useLines),
 * we simply do not need to worry about them.
 */
suspend fun main(): Unit = coroutineScope {
	val job = Job()
	launch(job) {
		try {
			delay(Random.nextLong(2000))
			println("Done")
		} finally {
			print("Will always be printed")
		}
	}
	delay(1000)
	job.cancelAndJoin()
}
// Will always be printed
// (or)
// Done
// Will always be printed