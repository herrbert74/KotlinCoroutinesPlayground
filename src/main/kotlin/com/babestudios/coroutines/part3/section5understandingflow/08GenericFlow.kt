package com.babestudios.coroutines.part3.section5understandingflow

/**
 * The last change we need is to replace String with a generic type parameter in
 * order to allow emitting and collecting any type of value.
 */
fun interface FlowCollector<T> {
	suspend fun emit(value: T)
}

interface Flow<T> {
	suspend fun collect(collector: FlowCollector<T>)
}

fun <T> flow(
	builder: suspend FlowCollector<T>.() -> Unit
) = object : Flow<T> {
	override suspend fun collect(
		collector: FlowCollector<T>
	) {
		collector.builder()
	}
}

suspend fun main() {
	val f: Flow<String> = flow {
		emit("A")
		emit("B")
		emit("C")
	}
	f.collect { print(it) } // ABC
	f.collect { print(it) } // ABC
}

/**
 * That’s it! This is nearly exactly how Flow, FlowCollector, and flow are implemented.
 * When you call collect, you invoke the lambda expression from the flow builder call.
 * When this expression calls emit, it calls the lambda expression specified when collect was called.
 * This is how it works.
 * The presented builder is the most basic way to create a flow. Later, we’ll learn about other builders, but they
 * generally just use flow under the hood.
 */

fun <T> Iterator<T>.asFlow(): Flow<T> = flow {
	forEach { value ->
		emit(value)
	}
}

fun <T> Sequence<T>.asFlow(): Flow<T> = flow {
	forEach { value ->
		emit(value)
	}
}

fun <T> flowOf(vararg elements: T): Flow<T> = flow {
	for (element in elements) {
		emit(element)
	}
}