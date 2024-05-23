package com.babestudios.coroutines.part3.section2select

import kotlinx.coroutines.async
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.selects.select

/**
 * Let’s say that we want to request data from multiple sources, but we’re only interested in the fastest response.
 * The easiest way to achieve this is to start these requests in async processes; then, use the select function as an
 * expression, and await different values inside it. Inside select, we can call onAwait on Deferred value,
 * which specifies a possible select expression result. Inside its lambda expression, you can transform the value.
 * In the example below, we just return an async result, so the select expression will complete once the first async
 * task is completed, then it will return its result.
 *
 * Notice that after the select expression, we call coroutineContext.cancelChildren() to cancel all the coroutines
 * that were started inside the select expression. This is important because without it, coroutineScope would not
 * complete until all the coroutines started inside its scope are completed.
 */
suspend fun requestData1(): String {
	delay(100_000)
	return "Data1"
}

suspend fun requestData2(): String {
	delay(1000)
	return "Data2"
}

suspend fun askMultipleForData(): String = coroutineScope {
	select {
		async { requestData1() }.onAwait { it }
		async { requestData2() }.onAwait { it }
	}.also { coroutineContext.cancelChildren() }
}

suspend fun main(): Unit = coroutineScope {
	println(askMultipleForData())
}