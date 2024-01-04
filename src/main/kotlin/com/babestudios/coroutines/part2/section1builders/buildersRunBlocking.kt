package com.babestudios.coroutines.part2.section1builders

import kotlinx.coroutines.*

/*
 * RunBlocking is not an extension on CoroutineScope, so it can only be the root coroutine.
 * You do not need Thread.sleep, because it's also blocking the thread!
 * Use this in main functions and test.
 */
fun main() {
	runBlocking {
		delay(1000L)
		println("World!")
	}
	runBlocking {
		delay(1000L)
		println("World!")
	}
	runBlocking {
		delay(1000L)
		println("World!")
	}
	println("Hello,")
}
