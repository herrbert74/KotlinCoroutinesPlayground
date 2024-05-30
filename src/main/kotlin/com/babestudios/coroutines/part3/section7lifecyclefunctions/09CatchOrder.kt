package com.babestudios.coroutines.part3.section7lifecyclefunctions

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onEmpty

/**
 * The catch will only react to the exceptions thrown in the function defined upstream (you can imagine that the
 * exception needs to be caught as it flows down).
 */
suspend fun main(): Unit {
	flowOf("Message1")
		.catch { emit("Error") }
		.onEach { throw Error(it) }
		.collect { println("Collected $it") }
}