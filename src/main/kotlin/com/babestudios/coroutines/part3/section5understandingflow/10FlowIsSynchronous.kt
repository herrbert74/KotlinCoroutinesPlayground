package com.babestudios.coroutines.part3.section5understandingflow

import kotlinx.coroutines.delay

/**
 * Notice that Flow is synchronous by nature, just like suspending functions: the collect call is suspended until the
 * flow is completed. This also means that a flow doesn’t start any new coroutines. Its concrete steps can do it, just
 * like suspending functions can start coroutines, but this is not the default behavior for suspending functions.
 * Most flow processing steps are executed synchronously, which is why a delay inside onEach introduces a delay between
 * each element, not before all elements, as you might expect.
 * THERE IS A DELAY ***BEFORE*** EACH ELEMENT!?
 */
suspend fun main() {
	flowOf("A", "B", "C")
		.onEach { delay(1000)
			println("delay")
		}
		.collect { println(it) }
}
