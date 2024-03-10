package com.babestudios.coroutines.part2.section4cancellation

import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Sometimes, we truly need to use a suspending call when a coroutine is already cancelled.
 * For instance, we might need to roll back changes in a database.
 * In this case, the preferred way is to wrap this call with the withContext(NonCancellable) function.
 * We will later explain in detail how withContext works.
 * For now, all we need to know is that it changes the context of a block of code.
 * Inside withContext, we used the NonCancellable object, which is a Job that cannot be cancelled.
 * So, inside the block the job is in the active state, and we can call whatever suspending functions we want.
 */
suspend fun main(): Unit = coroutineScope {
	val job = Job()
	launch(job) {
		try {
			delay(200)
			println("Coroutine finished")
		} finally {
			println("Finally")
			withContext(NonCancellable) {
				delay(1000L)
				println("Cleanup done")
			}
		}
	}
	delay(100)
	job.cancelAndJoin()
	println("Done")
}
// Finally
// Cleanup done
// Done