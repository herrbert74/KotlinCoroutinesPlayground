package com.babestudios.coroutines.part3.section6flowbuilding

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

/**
 * The flow builder is the most basic way to create a flow. All other options are based on it.
 *
 * public fun <T> flowOf(vararg elements: T): Flow<T> = flow { for (element in elements) {
 *         emit(element)
 *     }
 * }
 *
 * When we understand how this builder works, we will understand how flow works. flow builder is very simple under the
 * hood: it just creates an object implementing the Flow interface, which just calls the block function inside the
 * collect method.
 */
//fun <T> flow(
//	block: suspend FlowCollector<T>.() -> Unit
//): Flow<T> = object : Flow<T>() {
//	override suspend fun collect(collector: FlowCollector<T>) {
//		collector.block()
//	}
//}
//
//interface Flow<out T> {
//	suspend fun collect(collector: FlowCollector<T>)
//}
//
//fun interface FlowCollector<in T> {
//	suspend fun emit(value: T)
//}

/**
 * Knowing this, let’s analyze how the following code works:
 */
fun main() = runBlocking {
	flow { // 1
		emit("A")
		emit("B")
		emit("C")
	}.collect { value -> // 2
		println(value)
	}
}

/**
 * When we call a flow builder, we just create an object. However, calling collect means calling the block function
 * on the collector interface. The block function in this example is the lambda expression defined at 1.
 * Its receiver is the collector, which is defined at 2 with a lambda expression. When we define a function interface
 * (like FlowCollector) using a lambda expression, the body of this lambda expression will be used as the implementation
 * of the only function expected by this interface, that is emit in this case. So, the body of the emit function is
 * println(value). Thus, when we call collect, we start executing the lambda expression defined at 1, and when it
 * calls emit, it calls the lambda expression defined at 2. This is how flow works. Everything else is built on top of
 * that.
 */