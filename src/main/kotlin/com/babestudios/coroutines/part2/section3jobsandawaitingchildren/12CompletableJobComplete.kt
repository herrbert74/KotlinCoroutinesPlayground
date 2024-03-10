package com.babestudios.coroutines.part2.section3jobsandawaitingchildren

import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * The complete function is often used after we start the last coroutine
 * Thanks to this, we can just wait for the job completion using the join function.
 */
suspend fun main(): Unit = coroutineScope { val job = Job()
	launch(job) { // the new job replaces one from parent
		delay(1000)
		println("Text 1")
	}
	launch(job) { // the new job replaces one from parent
		delay(2000)
		println("Text 2")
	}
	job.complete()
	job.join() }
// (1 sec)
// Text 1
// (1 sec)
// Text 2