package com.babestudios.coroutines.part2.section3jobsandawaitingchildren

import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * A Job can be created without a coroutine using the Job() factory function.
 * It creates a job that isn’t associated with any coroutine and can be used as a context.
 * This also means that we can use such a job as a parent of many coroutines.
 *
 * A common mistake is to create a job using the Job() factory function, use it as a parent for some coroutines,
 * and then use join on the job. Such a program will never end because Job is still in an active state,
 * even when all its children are finished. This is because this context is still ready to be used by other coroutines.
 */
suspend fun main(): Unit = coroutineScope {
	val job = Job()
	launch(job) { // the new job replaces one from parent
		delay(1000)
		println("Text 1")
	}
	launch(job) { // the new job replaces one from parent
		delay(2000)
		println("Text 2")
	}
	job.join() // Here we will await forever
	println("Will not be printed")
}
// (1 sec)
// Text 1
// (1 sec)
// Text 2
// (runs forever)