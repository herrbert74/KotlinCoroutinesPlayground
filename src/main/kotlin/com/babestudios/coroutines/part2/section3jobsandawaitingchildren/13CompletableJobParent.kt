package com.babestudios.coroutines.part2.section3jobsandawaitingchildren

import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * You can pass a reference to the parent as an argument of the Job function.
 * Thanks to this, such a job will be cancelled when the parent is.
 */
suspend fun main(): Unit = coroutineScope {
	val parentJob = Job()
	val job = Job(parentJob)
	launch(job) {
		delay(1000)
		println("Text 1")
	}
	launch(job) {
		delay(2000)
		println("Text 2")
	}
	delay(1100)
	parentJob.cancel()
	job.children.forEach { it.join() }
}
// Text 1