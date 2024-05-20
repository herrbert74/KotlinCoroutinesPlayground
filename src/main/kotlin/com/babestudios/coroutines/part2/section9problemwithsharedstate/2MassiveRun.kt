package com.babestudios.coroutines.part2.section9problemwithsharedstate

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

/**
 * To understand why the result is not 1,000,000, imagine a scenario in which two threads try to increment the same
 * number at the same time. Let’s say that the initial value is 0. The first thread takes the current value 0,
 * and then the processor decides to switch to the second thread. The second thread takes 0 as well,
 * increments it to 1, and stores it in the variable. We switch to the first thread where it has finished: it has 0,
 * so it increments it to 1 and stores it. As a result, the variable is 1 but it should be 2.
 * This is how some operations are lost.
 */
private var counter = 0

fun main() = runBlocking {
	massiveRun {
		counter++
	}
	println(counter) // ~567231
}

private suspend fun massiveRun(action: suspend () -> Unit) =
	withContext(Dispatchers.Default) {
		repeat(1000) {
			launch {
				repeat(1000) { action() }
			}
		}
	}