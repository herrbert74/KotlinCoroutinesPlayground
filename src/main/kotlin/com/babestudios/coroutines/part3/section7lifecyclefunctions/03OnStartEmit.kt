package com.babestudios.coroutines.part3.section7lifecyclefunctions

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

/**
 * It is good to know that in onStart (as well as in onCompletion, onEmpty and catch) we can emit elements.
 * Such elements will flow downstream from this place.
 */
suspend fun main() {
	flowOf(1, 2)
		.onEach {
			delay(1000)
			println("onEach")
		}
		.onStart { emit(0) }
		.collect { println(it) }
}
