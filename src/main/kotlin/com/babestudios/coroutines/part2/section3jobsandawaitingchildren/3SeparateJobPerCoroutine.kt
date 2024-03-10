package com.babestudios.coroutines.part2.section3jobsandawaitingchildren

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * There is a very important rule:
 * Job is the only coroutine context that is not inherited by a coroutine from a coroutine.
 * Every coroutine creates its own Job, and the job from an argument or parent coroutine is used as a parent of this
 * new job.
 */
fun main(): Unit = runBlocking {
	val name = CoroutineName("Some name")
	val job = Job()
	launch(name + job) {
		val childName = coroutineContext[CoroutineName]
		println(childName == name) // true
		val childJob = coroutineContext[Job]
		println(childJob == job) // false
		println(childJob == job.children.first()) // true

	}
}