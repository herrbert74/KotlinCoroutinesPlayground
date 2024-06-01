package com.babestudios.coroutines.part3.section9sharedflow

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

/**
 * WhileSubscribed() - starts listening on the flow when the first subscriber appears; it stops when the last subscriber
 * disappears. If a new subscriber appears when our SharedFlow is stopped, it will start again. WhileSubscribed has
 * additional optional configuration parameters: stopTimeoutMillis (how long to listen after the last subscriber
 * disappears, 0 by default) and replayExpirationMillis (how long to keep replay after stopping, Long.MAX_VALUE by
 * default).
 */
suspend fun main(): Unit = coroutineScope {
	val flow = flowOf("A", "B", "C", "D")
		.onStart { println("Started") }
		.onCompletion { println("Finished") }
		.onEach { delay(1000) }
	val sharedFlow = flow.shareIn(
		scope = this,
		started = SharingStarted.WhileSubscribed(),
	)
	delay(3000)
	launch {
		println("#1 ${sharedFlow.first()}")
	}
	launch {
		println("#2 ${sharedFlow.take(2).toList()}")
	}
	delay(3000)
	launch {
		println("#3 ${sharedFlow.first()}")
	}
}

