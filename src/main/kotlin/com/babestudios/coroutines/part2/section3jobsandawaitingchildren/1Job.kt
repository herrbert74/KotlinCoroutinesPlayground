package com.babestudios.coroutines.part2.section3jobsandawaitingchildren

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * The code below presents Job in different states.
 */
suspend fun main() = coroutineScope {
	// Job created with a builder is active
	val job = Job()
	println(job) // JobImpl{Active}@ADD
	// until we complete it with a method
	job.complete()
	println(job) // JobImpl{Completed}@ADD
	// launch is initially active by default
	val activeJob = launch {
		delay(1000)
	}
	println(activeJob) // StandaloneCoroutine{Active}@ADD
	// here we wait until this job is done
	activeJob.join() // (1 sec)
	println(activeJob) // StandaloneCoroutine{Completed}@ADD
	// launch started lazily is in New state
	val lazyJob = launch(start = CoroutineStart.LAZY) {
		delay(1000)
	}
	println(lazyJob) // LazyStandaloneCoroutine{New}@ADD
	// we need to start it, to make it active
	lazyJob.start()
	println(lazyJob) // LazyStandaloneCoroutine{Active}@ADD
	lazyJob.join() // (1 sec)
	println(lazyJob) //LazyStandaloneCoroutine{Completed}@ADD
}