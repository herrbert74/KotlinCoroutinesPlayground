package com.babestudios.coroutines.part2.section10testing

import kotlinx.coroutines.delay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher

/**
 * Another way to push time is using advanceTimeBy with a concrete number of milliseconds.
 * This function advances time and executes all operations that happened in the meantime.
 * This means that if we push by 2 milliseconds, everything that was delayed by less than that time will be resumed.
 * To resume operations scheduled exactly at the second millisecond, we need to additionally invoke the runCurrent
 * function.
 */
fun main() {
	val testDispatcher = StandardTestDispatcher()

	CoroutineScope(testDispatcher).launch {
		delay(1)
		println("Done1")
	}
	CoroutineScope(testDispatcher).launch {
		delay(2)
		println("Done2")
	}
	testDispatcher.scheduler.advanceTimeBy(2) // Done
	testDispatcher.scheduler.runCurrent() // Done2
}
