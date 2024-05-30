package com.babestudios.coroutines.part3.section7lifecyclefunctions

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

/**
 * Therefore, it is common practice to move the operation from collect to onEach and place it before the catch. This is
 * specifically useful if we suspect that collect might raise an exception. If we move the operation from collect, we
 * can be sure that catch will catch all exceptions.
 */
val flowTerminalFix = flow {
	emit("Message1")
	emit("Message2")
}

suspend fun main() {
	flowTerminalFix.onStart { println("Before") }
		.onEach { throw MyError() }
		.catch { println("Caught $it") }
		.collect { println(it) }
}
