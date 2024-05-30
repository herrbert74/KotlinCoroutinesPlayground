package com.babestudios.coroutines.part3.section7lifecyclefunctions

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach

/**
 * Flow can be imagined as a pipe in which requests for next values flow in one direction, and the corresponding
 * produced values flow in the other direction. When flow is completed or an exception occurs, this information is
 * also propagated, and it closes the intermediate steps on the way. So, as they all flow, we can listen for values,
 * exceptions, or other characteristic events (like starting or completing). To do this, we use methods such as onEach,
 * onStart, onCompletion, onEmpty and catch. Let’s explain these one by one.
 *
 * To react to each flowing value, we use the onEach function.
 *
 * The onEach lambda expression is suspending, and elements are processed one after another in order (sequentially).
 * So, if we add delay in onEach, we will delay each value as it flows.
 */
suspend fun main() {
	flowOf(1, 2, 3, 4)
		.onEach { delay(1000) }
		.collect { print(it) }
}
