package com.babestudios.coroutines.part3.section5understandingflow

import kotlinx.coroutines.delay

/**
 * Flow can be considered a bit more complicated than suspending lambda expressions with a receiver.
 * However, its power lies in all the functions defined for its creation, processing, and observation.
 * Most of them are actually very simple under the hood.
 * We will learn about them in the next chapters, but I want you to have the intuition that most of them are very
 * simple and can be easily constructed using flow, collect, and emit.
 *
 * Consider the map function that transforms each element of a flow. It creates a new flow, so it uses the flow builder.
 * When its flow is started, it needs to start the flow it wraps; so, inside the builder, it calls the collect method.
 * Whenever an element is received, map transforms this element and then emits it to the new flow.
 */
fun <T, R> Flow<T>.map2(
	transformation: suspend (T) -> R
): Flow<R> = flow {
	collect {
		emit(transformation(it))
	}
}

suspend fun main() {
	flowOf("A", "B", "C")
		.map2 {
			delay(1000)
			it.lowercase()
		}
		.collect { println(it) }
}

/**
 * The behavior of most of the methods that we’ll learn about in the next chapters is just as simple.
 * It is important to understand this because it not only helps us better understand how our code works
 * but also teaches us how to write similar functions.
 */
fun <T> Flow<T>.filter(
	predicate: suspend (T) -> Boolean
): Flow<T> = flow {
	collect {
		if (predicate(it)) {
			emit(it)
		}
	}
}

fun <T> Flow<T>.onEach(
	action: suspend (T) -> Unit
): Flow<T> = flow {
	collect {
		action(it)
		emit(it)
	}
}

// simplified implementation
fun <T> Flow<T>.onStart(
	action: suspend () -> Unit
): Flow<T> = flow {
	action()
	collect {
		emit(it)
	}
}