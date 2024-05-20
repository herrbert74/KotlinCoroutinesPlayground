package com.babestudios.coroutines.part2.section10testing

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler

/**
 * To use TestCoroutineScheduler on coroutines, we should use a dispatcher that supports it. The standard option is
 * StandardTestDispatcher. Unlike most dispatchers, it is not used just to decide on which thread a coroutine should
 * run. Coroutines started with such a dispatcher will not run until we advance virtual time.
 * The most typical way to do this is by using advanceUntilIdle, which advances virtual time and invokes all the
 * operations that would be called during that time if this were real time.
 */
fun main() {
	val scheduler = TestCoroutineScheduler()
	val testDispatcher = StandardTestDispatcher(scheduler)
	CoroutineScope(testDispatcher).launch {
		println("Some work 1")
		delay(1000)
		println("Some work 2")
		delay(1000)
		println("Coroutine done")
	}
	println("[${scheduler.currentTime}] Before")
	scheduler.advanceUntilIdle()
	println("[${scheduler.currentTime}] After")
}
