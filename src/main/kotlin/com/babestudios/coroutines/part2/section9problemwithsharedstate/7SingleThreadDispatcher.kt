package com.babestudios.coroutines.part2.section9problemwithsharedstate

import kotlinx.coroutines.*
import java.util.concurrent.Executors

/**
 * We saw a dispatcher with parallelism limited to a single thread in the Dispatchers chapter.
 * This is the easiest solution for most problems with shared states.
 */
private val dispatcher = Dispatchers.IO
	.limitedParallelism(1)

private var counter = 0

fun main() = runBlocking {
	massiveRun {
		withContext(dispatcher) {
			counter++
		}
	}
	println(counter) // 1000000
}


private suspend fun massiveRun(action: suspend () -> Unit) =
	withContext(Dispatchers.Default) {
		repeat(1000) {
			launch {
				repeat(1000) { action() }
			}
		}
	}