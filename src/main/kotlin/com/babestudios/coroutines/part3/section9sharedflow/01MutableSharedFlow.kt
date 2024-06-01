package com.babestudios.coroutines.part3.section9sharedflow

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

/**
 * Flow is typically cold, so its values are calculated on demand. However, there are cases in which we want multiple
 * receivers to be subscribed to one source of changes. This is where we use SharedFlow, which is conceptually similar
 * to a mailing list. We also have StateFlow, which is similar to an observable value. Let’s explain them both step by
 * step.
 *
 * Let’s start with MutableSharedFlow, which is like a broadcast channel: everyone can send (emit) messages which will
 * be received by every coroutine that is listening (collecting).
 */
suspend fun main(): Unit = coroutineScope {
	val mutableSharedFlow = MutableSharedFlow<String>(replay = 0) // or MutableSharedFlow<String>()
	launch {
		mutableSharedFlow.collect {
			println("#1 received $it")
		}
	}
	launch {
		mutableSharedFlow.collect {
			println("#2 received $it")
		}
	}
	delay(1000)
	mutableSharedFlow.emit("Message1")
	mutableSharedFlow.emit("Message2")
}

/**
 * The above program never ends because the coroutineScope is waiting for the coroutines that were started with launch
 * and which keep listening on MutableSharedFlow. Apparently, MutableSharedFlow is not closable, so the only way to fix
 * this problem is to cancel the whole scope.
 */