package com.babestudios.coroutines.part3.section9sharedflow

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * stateIn is a function that transforms Flow<T> into StateFlow<T>. It can only be called with a scope, but it is a
 * suspending function. Remember that StateFlow needs to always have a value; so, if you don’t specify it, then you
 * need to wait until the first value is calculated.
 */
suspend fun main() = coroutineScope {
	val flow = flowOf("A", "B", "C")
		.onEach { delay(1000) }
		.onEach { println("Produced $it") }
	val stateFlow: StateFlow<String> = flow.stateIn(this)
	println("Listening")
	println(stateFlow.value)
	stateFlow.collect { println("Received $it") }
	println("Listening")
}
