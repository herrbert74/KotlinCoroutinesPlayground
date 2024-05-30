package com.babestudios.coroutines.part3.section7lifecyclefunctions

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

/**
 * Notice that using catch does not protect us from an exception in the terminal operation (because catch cannot be
 * placed after the last operation). So, if there is an exception in the collect, it won’t be caught, and an error will
 * be thrown.
 */
val flowTerminal = flow {
	emit("Message1")
	emit("Message2")
}

suspend fun main() {
	flowTerminal.onStart { println("Before") }
		.catch { println("Caught $it") }
		.collect { throw MyError() }
}
