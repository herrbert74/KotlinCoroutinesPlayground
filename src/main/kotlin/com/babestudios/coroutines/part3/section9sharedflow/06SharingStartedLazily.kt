package com.babestudios.coroutines.part3.section9sharedflow

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

/**
 * SharingStarted.Lazily-starts listening when the first subscriber appears. This guarantees that this first subscriber
 * gets all the emitted values, while subsequent subscribers are only guaranteed to get the most recent replay values.
 * The upstream flow continues to be active even when all subscribers disappear, but only the most recent replay
 * values are cached without subscribers.
 */
suspend fun main(): Unit = coroutineScope {
	val flow1 = flowOf("A", "B", "C")
	val flow2 = flowOf("D")
		.onEach { delay(1000) }
	val sharedFlow = merge(flow1, flow2).shareIn(
		scope = this,
		started = SharingStarted.Lazily,
	)
	delay(100)
	launch {
		sharedFlow.collect { println("#1 $it") }
	}
	delay(1000)
	launch {
		sharedFlow.collect { println("#2 $it") }
	}
}

