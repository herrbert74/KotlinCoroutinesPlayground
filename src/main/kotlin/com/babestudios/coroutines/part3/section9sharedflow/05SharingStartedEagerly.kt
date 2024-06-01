package com.babestudios.coroutines.part3.section9sharedflow

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

/**
 * SharingStarted.Eagerly-immediately starts listening for values and sending them to a flow. Notice that if you have a
 * limited replay value and your values appear before you start subscribing, you might lose some values (if your replay
 * is 0, you will lose all such values).
 */
suspend fun main(): Unit = coroutineScope {
	val flow = flowOf("A", "B", "C")
	val sharedFlow: SharedFlow<String> = flow.shareIn(
		scope = this,
		started = SharingStarted.Eagerly,
	)
	delay(100)
	launch {
		sharedFlow.collect { println("#1 $it") }
	}
	print("Done")
}


