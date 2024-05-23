package com.babestudios.coroutines.part3.section3hotvscold

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

/**
 * The same processing using Flow is very different. Since it is a cold data source, the production happens on demand.
 * This means that flow is not a builder and does no processing. It is only a definition of how elements should be
 * produced that will be used when a terminal operation (like collect) is used. This is why the flow builder does not
 * need a CoroutineScope. It will run in the scope from the terminal operation that executed it (it takes the scope
 * from the suspending function’s continuation, just like coroutineScope and other coroutine scope functions).
 * Each terminal operation on a flow starts processing from scratch. Compare the examples above and below because
 * they show the key differences between Channel and Flow.
 */
private fun makeFlow() = flow {
	println("Flow started")
	for (i in 1..3) {
		delay(1000)
		emit(i)
	}
}

suspend fun main() = coroutineScope {
	val flow = makeFlow()
	delay(1000)
	println("Calling flow...")
	flow.collect { value -> println(value) }
	println("Consuming again...")
	flow.collect { value -> println(value) }
}
