package com.babestudios.coroutines.part2.section10testing

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher

/**
 * It is important to notice that StandardTestDispatcher does not advance time by itself.
 * We need to do this, otherwise our coroutine will never be resumed.
 */
fun main() {
	val testDispatcher = StandardTestDispatcher()

	runBlocking(testDispatcher) {
		delay(1)
		println("Coroutine done")
	}
}
