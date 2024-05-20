package com.babestudios.coroutines.part2.section10testing

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher

/**
 * StandardTestDispatcher creates TestCoroutineScheduler by default, so we do not need to do so explicitly.
 * We can access it with the scheduler property.
 */
fun main() {
	val dispatcher = StandardTestDispatcher()
	CoroutineScope(dispatcher).launch {
		println("Some work 1")
		delay(1000)
		println("Some work 2")
		delay(1000)
		println("Coroutine done")
	}
	println("[${dispatcher.scheduler.currentTime}] Before")
	dispatcher.scheduler.advanceUntilIdle()
	println("[${dispatcher.scheduler.currentTime}] After")
}
