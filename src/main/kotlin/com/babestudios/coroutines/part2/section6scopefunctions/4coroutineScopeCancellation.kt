package com.babestudios.coroutines.part2.section6scopefunctions

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * In the next snippet, you can observe how cancellation works.
 * A cancelled parent leads to the cancellation of unfinished children.
 */
fun main(): Unit = runBlocking {
	val job = launch(CoroutineName("Parent")) {
		longTask()
	}
	delay(1500)
	job.cancel()
}
// [Parent] Finished task 1