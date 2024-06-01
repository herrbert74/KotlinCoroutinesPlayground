package com.babestudios.coroutines.part3.section9sharedflow

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

/**
 * MutableSharedFlow can also keep sending messages. If we set the replay parameter (it defaults to 0), the defined
 * number of last values will be kept. If a coroutine now starts observing, it will receive these values first. This
 * cache can also be reset with resetReplayCache.
 */
suspend fun main(): Unit = coroutineScope {
	val mutableSharedFlow = MutableSharedFlow<String>(replay = 2)
	mutableSharedFlow.emit("Message1")
	mutableSharedFlow.emit("Message2")
	mutableSharedFlow.emit("Message3")
	println(mutableSharedFlow.replayCache)
	// [Message2, Message3]
	launch {
		mutableSharedFlow.collect {
			println("#1 received $it")
		}
		// #1 received Message2
		// #1 received Message3
	}
	delay(100)
	mutableSharedFlow.resetReplayCache()
	println(mutableSharedFlow.replayCache) // []
}

/**
 * MutableSharedFlow is conceptually similar to RxJava Subjects. When the replay parameter is set to 0, it is similar to
 * a PublishSubject. When replay is 1, it is similar to a BehaviorSubject. When replay is Int.MAX_VALUE, it is similar
 * to ReplaySubject.
 */