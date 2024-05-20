package com.babestudios.coroutines.part2.section10testing

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlin.random.Random
import kotlin.system.measureTimeMillis

/**
 * To see that virtual time is truly independent of real time, see the example below. Adding Thread.sleep will not
 * influence the coroutine with StandardTestDispatcher. Note also that the call to advanceUntilIdle takes only a few
 * milliseconds, so it does not wait for any real time. It immediately pushes the virtual time and executes coroutine
 * operations.
 */
fun main() {
	val dispatcher = StandardTestDispatcher()

	CoroutineScope(dispatcher).launch {
		delay(1000)
		println("Coroutine done")
	}

	Thread.sleep(Random.nextLong(2000)) // Does not matter
	// how much time we wait here, it will not influence
	// the result

	val time = measureTimeMillis {
		println("[${dispatcher.scheduler.currentTime}] Before")
		dispatcher.scheduler.advanceUntilIdle()
		println("[${dispatcher.scheduler.currentTime}] After")
	}
	println("Took $time ms")
}