package com.babestudios.coroutines.part3.section7lifecyclefunctions

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

/**
 * There are a few ways in which a flow can be completed. The most common one is when the flow builder is done (i.e.,
 * the last element has been sent), although this also happens in the case of an uncaught exception or a coroutine
 * cancellation. In all these cases, we can add a listener for flow completion by using the onCompletion method.
 */
suspend fun main() = coroutineScope {
	flowOf(1, 2)
		.onEach { delay(1000) }
		.onCompletion { println("Completed") }
		.collect { println(it) }
}
