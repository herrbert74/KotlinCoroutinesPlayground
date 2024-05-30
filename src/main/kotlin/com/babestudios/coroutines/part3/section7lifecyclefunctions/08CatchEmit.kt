package com.babestudios.coroutines.part3.section7lifecyclefunctions

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onEmpty

/**
 * The catch method stops an exception by catching it. The previous steps have already been completed, but catch can
 * still emit new values and keep the rest of the flow alive.
 */
val flowCatch = flow {
	emit("Message1")
	throw MyError()
}

suspend fun main(): Unit {
	flowCatch.catch { emit("Error") }
		.collect { println("Collected $it") }
}