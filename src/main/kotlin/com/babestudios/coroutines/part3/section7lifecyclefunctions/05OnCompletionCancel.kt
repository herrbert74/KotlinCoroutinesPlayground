package com.babestudios.coroutines.part3.section7lifecyclefunctions

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

/**
 * Cancel case
 */
suspend fun main() = coroutineScope {
	val job = launch {
		flowOf(1, 2)
			.onEach { delay(1000) }
			.onCompletion { println("Completed") }
			.collect { println(it) }
	}
	delay(1100)
	job.cancel()
}

