package com.babestudios.coroutines.part2.section3jobsandawaitingchildren

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * 1. Launch returns a Job
 * 2. Async returns Deferred, which is an interface extending Job, with a result
 * 3. Job is a CoroutineContext, so we can access it using coroutineContext[Job].
 * 	There is also an extension property 'job'
 */
fun main(): Unit = runBlocking {

	val job: Job = launch {
		delay(1000)
		println("Test")
	}

	val deferred: Deferred<String> = async {
		delay(1000)
		"Test Deferred"
	}
	val deferredJob: Job = deferred
	println((deferredJob as Deferred<*>).await())

	println(coroutineContext.job.isActive) // true

}