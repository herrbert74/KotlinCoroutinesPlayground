package com.babestudios.coroutines.part3.section7lifecyclefunctions

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onEmpty

/**
 * Uncaught exceptions in a flow immediately cancel this flow, and collect rethrows this exception. This behavior is
 * typical of suspending functions, and coroutineScope behaves the same way. Exceptions can be caught outside flow
 * using the classic try-catch block.
 */
val flowUncaught = flow {
	emit("Message1")
	throw MyError()
}

suspend fun main(): Unit {
	try {
		flowUncaught.collect { println("Collected $it") }
	} catch (e: MyError) {
		println("Caught")
	}
}
