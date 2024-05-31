package com.babestudios.coroutines.part3.section8flowprocessing

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach

/**
 * It is important to know that when we use merge the elements from one flow do not wait for another flow. For instance,
 * in the example below, elements from the first flow are delayed, but this does not stop the elements from the second
 * flow.
 */
suspend fun main() {
	val ints: Flow<Int> = flowOf(1, 2, 3)
		.onEach { delay(1000) }
	val doubles: Flow<Double> = flowOf(0.1, 0.2, 0.3)
	val together: Flow<Number> = merge(ints, doubles)
	together.collect { println(it) }
}

/**
 * We use merge when we have multiple sources of events that should lead to the same actions.
 */
//fun listenForMessages() {
//	merge(userSentMessages, messagesNotifications)
//		.onEach { displayMessage(it) }
//		.launchIn(scope)
//}