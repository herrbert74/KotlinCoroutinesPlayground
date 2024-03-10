package com.babestudios.coroutines.part2.section4cancellation

import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

/**
 * Another option is to track the state of the job.
 * Inside a coroutine builder, this (the receiver) references the scope of this builder.
 * CoroutineScope has a context we can reference using the coroutineContext property.
 * Thus, we can access the coroutine job (coroutineContext[Job] or coroutineContext.job)
 * and check what its current state is. Since a job is often used to check if a coroutine is active,
 * the Kotlin Coroutines library provides a function to simplify that:
 *
 *
 * public val CoroutineScope.isActive: Boolean
 * 	get() = coroutineContext[Job]?.isActive ?: true
 *
 * We can use the isActive property to check if a job is still active and stop calculations when it is inactive.
 */
suspend fun main(): Unit = coroutineScope {
	val job = Job()
	launch(job) {
		do {
			Thread.sleep(200)
			println("Printing")
		} while (isActive)
	}
	delay(1100)
	job.cancelAndJoin()
	println("Cancelled successfully")
}
// Printing
// Printing
// Printing
// Printing
// Printing
// Printing
// Cancelled successfully