package com.babestudios.coroutines.part3.section9sharedflow

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

/**
 * Beware that StateFlow is conflated, so slower observers might not receive some intermediate state changes.
 * To receive all events, use SharedFlow. This behavior is by design. StateFlow represents the current state, and we
 * might assume that nobody is interested in an outdated state.
 */
suspend fun main(): Unit = coroutineScope {
	val state = MutableStateFlow('X')
	launch {
		for (c in 'A'..'E') {
			delay(300)
			state.value = c
			// or state.emit(c)
		}
	}
	state.collect {
		delay(1000)
		println(it)
	}
}
