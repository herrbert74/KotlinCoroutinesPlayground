package com.babestudios.coroutines.part2.section7dispatchers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

/**
 * Dispatchers.IO has a special behavior defined for the limitedParallelism function.
 * It creates a new dispatcher with an independent pool of threads.
 * What is more, this pool is not limited to 64 as we can decide to limit it to as many threads as we want.
 * For example, imagine you start 100 coroutines, each of which blocks a thread for a second.
 * If you run these coroutines on Dispatchers.IO, it will take 2 seconds.
 * If you run them on Dispatchers.IO with limitedParallelism set to 100 threads, it will take 1 second.
 * Execution time for both dispatchers can be measured at the same time because the limits of these two dispatchers
 * are independent anyway.
 */
@OptIn(ExperimentalCoroutinesApi::class)
suspend fun main(): Unit = coroutineScope {
	launch {
		printCoroutinesTime(Dispatchers.IO)
		// Dispatchers.IO took: 2074
	}
	launch {
		val dispatcher = Dispatchers.IO.limitedParallelism(100)
		printCoroutinesTime(dispatcher)
		// LimitedDispatcher@XXX took: 1082
	}
}

suspend fun printCoroutinesTime(
	dispatcher: CoroutineDispatcher
) {
	val test = measureTimeMillis {
		coroutineScope {
			repeat(100) {
				launch(dispatcher) {
					Thread.sleep(1000)
				}
			}
		}
	}
	println("$dispatcher took: $test")
}
