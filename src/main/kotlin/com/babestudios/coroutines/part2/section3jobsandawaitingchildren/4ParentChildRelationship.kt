package com.babestudios.coroutines.part2.section3jobsandawaitingchildren

import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * The parent can reference all its children, and the children can refer to the parent.
 * This parent-child relationship (Job reference storing) enables the implementation of cancellation and exception
 * handling inside a coroutine’s scope.
 */
fun main(): Unit = runBlocking {
	val job: Job = launch {
		delay(1000)
	}
	val parentJob: Job = coroutineContext.job
	// or coroutineContext[Job]!!
	println(job == parentJob) // false
	val parentChildren: Sequence<Job> = parentJob.children
	println(parentChildren.first() == job) // true
}