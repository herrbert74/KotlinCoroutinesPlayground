package com.babestudios.coroutines.part2.section7dispatchers

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * For all dispatchers using multiple threads, we need to consider the shared state problem.
 * Notice that in the example below 10,000 coroutines are increasing i by 1.
 * So, its value should be 10,000, but it is a smaller number. This is a result of a shared state (i property)
 * modification on multiple threads at the same time.
 */
private var i = 0

suspend fun main(): Unit = coroutineScope {
	repeat(10_000) {
		launch(Dispatchers.IO) { // or Default
			i++
		}
	}
	delay(1000)
	println(i) // ~9930
}
