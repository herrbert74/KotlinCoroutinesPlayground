package com.babestudios.coroutines.part2.section3jobsandawaitingchildren

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * The first important advantage of a job is that it can be used to wait until the coroutine is completed.
 * For that, we use the join method. This is a suspending function that suspends until a concrete job reaches a final
 * state (either Completed or Cancelled).
 */
fun main(): Unit = runBlocking {
	val job1 = launch {
		delay(1000)
		println("Test1")
	}
	val job2 = launch {
		delay(2000)
		println("Test2")
	}
	job1.join()
	job2.join()
	println("All tests are done")
}
// (1 sec)
// Test1
// (1 sec)
// Test2
// All tests are done