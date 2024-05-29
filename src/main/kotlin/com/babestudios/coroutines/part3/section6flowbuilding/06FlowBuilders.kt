package com.babestudios.coroutines.part3.section6flowbuilding

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * The most popular way to make a flow is using the flow builder, which we’ve already used in previous chapters.
 * It behaves similarly to sequence builder for building a sequence, or produce builder for building a channel.
 * We start the builder with the flow function call, and inside its lambda expression we emit the next values using the
 * emit function. We can also use emitAll to emit all the values from Channel or Flow (emitAll(flow) is shorthand for
 * flow.collect { emit(it) }).
 */
fun makeFlow(): Flow<Int> = flow {
	repeat(3) { num ->
		delay(1000)
		emit(num)
	}
}

suspend fun main() {
	makeFlow()
		.collect { println(it) }
}