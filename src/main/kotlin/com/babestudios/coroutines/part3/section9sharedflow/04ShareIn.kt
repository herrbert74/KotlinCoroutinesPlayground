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
 * Flow is often used to observe changes, like user actions, database modifications, or new messages. We already know
 * the different ways in which these events can be processed and handled. We’ve learned how to merge multiple flows
 * into one. But what if multiple classes are interested in these changes, and we would like to turn one flow into
 * multiple flows? The solution is SharedFlow, and the easiest way to turn a Flow into a SharedFlow is by using the
 * shareIn function.
 */
suspend fun main(): Unit = coroutineScope {
	val flow = flowOf("A", "B", "C")
		.onEach { delay(1000) }
	val sharedFlow: SharedFlow<String> = flow.shareIn(
		scope = this,
		started = SharingStarted.Eagerly,
		// replay = 0 (default)
	)
	delay(500)
	launch {
		sharedFlow.collect { println("#1 $it") }
	}
	delay(1000)
	launch {
		sharedFlow.collect { println("#2 $it") }
	}
	delay(1000)
	launch {
		sharedFlow.collect { println("#3 $it") }
	}
}
/**
 * The shareIn function creates a SharedFlow and sends elements from its Flow. Since we need to start a coroutine to
 * collect elements on flow, shareIn expects a coroutine scope as the first argument. The third argument is replay,
 * which is 0 by default. The second argument is interesting: started determines when listening for values should start,
 * depending on the number of listeners. The following options are supported (NEXT FILES):
 */


