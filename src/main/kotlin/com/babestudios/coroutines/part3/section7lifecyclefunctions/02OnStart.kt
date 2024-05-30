package com.babestudios.coroutines.part3.section7lifecyclefunctions

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

/**
 * The onStart function sets a listener that should be called immediately once the flow is started, i.e., once the
 * terminal operation is called. It is important to note that onStart does not wait for the first element: it is called
 * when we request the first element.
 */
suspend fun main() {
	flowOf(1, 2)
		.onEach {
			delay(1000)
			println("onEach")
		}
		.onStart { println("Before") }
		.collect { println(it) }
}
