package com.babestudios.coroutines.part2.section7dispatchers

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

/**
 * Dispatchers.IO is designed to be used when we block threads with I/O operations,
 * for instance, when we read/write files, use Android shared preferences, or call blocking functions.
 * The code below takes around 1 second because Dispatchers.IO allows more than 50 active threads at the same time.
 */
suspend fun main() {
	val time = measureTimeMillis {
		coroutineScope {
			repeat(50) {
				launch(Dispatchers.IO) {
					Thread.sleep(1000)
				}
			}
		}
	}
	println(time) // ~1000
}
