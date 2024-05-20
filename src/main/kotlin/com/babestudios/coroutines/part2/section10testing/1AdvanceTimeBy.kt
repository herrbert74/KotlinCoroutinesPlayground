package com.babestudios.coroutines.part2.section10testing

import kotlinx.coroutines.test.TestCoroutineScheduler

/**
 * When we call delay, our coroutine is suspended and resumed after a set time. This behavior can be altered thanks to
 * TestCoroutineScheduler from kotlinx-coroutines-test, which makes delay operate in virtual time, which is fully
 * simulated and does not depend on real time.
 */
fun main() {
	val scheduler = TestCoroutineScheduler()

	println(scheduler.currentTime) // 0
	scheduler.advanceTimeBy(1_000)
	println(scheduler.currentTime) // 1000
	scheduler.advanceTimeBy(1_000)
	println(scheduler.currentTime) // 2000
}