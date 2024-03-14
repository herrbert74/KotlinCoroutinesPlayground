package com.babestudios.coroutines.part2.section7dispatchers

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * A modern solution is to use Dispatchers.Default or Dispatchers.IO (if we block threads)
 * with parallelism limited to 1.
 */
private var i = 0

@OptIn(ExperimentalCoroutinesApi::class)
suspend fun main(): Unit = coroutineScope {
	val dispatcher = Dispatchers.Default.limitedParallelism(1)
	repeat(10000) {
		launch(dispatcher) {
			i++
		}
	}
	delay(1000)
	println(i) // 10000
}
