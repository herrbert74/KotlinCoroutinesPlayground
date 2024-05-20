package com.babestudios.coroutines.part2.section10testing

import kotlinx.coroutines.delay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher

/**
 * Here is a bigger example of using advanceTimeBy together with runCurrent.
 *
 * How does it work under the hood? When delay is called, it checks if the dispatcher (class with
 * ContinuationInterceptor key) implements the Delay interface (StandardTestDispatcher does).
 * For such dispatchers, it calls their scheduleResumeAfterDelay function instead of the one from the DefaultDelay,
 * which waits in real time.
 */
fun main() {
	val testDispatcher = StandardTestDispatcher()

	CoroutineScope(testDispatcher).launch {
		delay(2)
		print("Done")
	}

	CoroutineScope(testDispatcher).launch {
		delay(4)
		print("Done2")
	}

	CoroutineScope(testDispatcher).launch {
		delay(6)
		print("Done3")
	}

	for (i in 1..5) {
		print(".")
		testDispatcher.scheduler.advanceTimeBy(1)
		testDispatcher.scheduler.runCurrent()
	}
}
