package com.babestudios.coroutines.part3.section7lifecyclefunctions

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach

/**
 * At any point of flow building or processing, an exception might occur. Such an exception will flow down, closing
 * each processing step on the way; however, it can be caught and managed. To do so, we can use the catch method.
 * This listener receives the exception as an argument and allows you to perform recovering operations.
 */
class MyError : Throwable("My error")

val flow = flow {
	emit(1)
	emit(2)
	throw MyError()
}

suspend fun main() {
	flow.onEach { println("Got $it") }
		.catch { println("Caught $it") }
		.collect { println("Collected $it") }
}

/**
 * In the example above, notice that onEach does not react to an exception. The same happens with other functions like
 * map, filter etc. Only the onCompletion handler will be called.
 */
