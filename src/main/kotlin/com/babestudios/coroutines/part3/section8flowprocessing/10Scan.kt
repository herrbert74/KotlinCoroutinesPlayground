package com.babestudios.coroutines.part3.section8flowprocessing

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.scan

/**
 * There is an alternative to fold called scan. It is an intermediate operation that produces all intermediate
 * accumulator values.
 */
//fun main() {
//	val list = listOf(1, 2, 3, 4)
//	val res = list.scan(0) { acc, i -> acc + i }
//	println(res) // [0, 1, 3, 6, 10]
//}

/**
 * scan is useful withFlow because it produces a new value immediately after receiving one from the previous step.
 */
suspend fun main() {
	flowOf(1, 2, 3, 4)
		.onEach { delay(1000) }
		.scan(0) { acc, v -> acc + v }
		.collect { println(it) }
}

/**
 *
 * We can implement scan easily using the flow builder and collect. We first emit the initial value, then with each
 * new element we emit the result of the next value accumulation.
 *
 * fun <T, R> Flow<T>.scan(
 * 	initial: R,
 * 	operation: suspend (accumulator: R, value: T) -> R
 * ): Flow<R> = flow {
 * 	var accumulator: R = initial
 * 	emit (accumulator)
 * 	collect { value ->
 * 		accumulator = operation(accumulator, value)
 * 		emit(accumulator)
 * 	}
 * }
 *
 * The typical use case for scan is when we have a flow of updates or changes, and we
 * need an object that is the result of these changes.
 *
 * val userStateFlow: Flow<User> = userChangesFlow
 * 	.scan(user) { user, change -> user.withChange(change) }
 *
 * val messagesListFlow: Flow<List<Message>> = messagesFlow
 * 	.scan(messages) { acc, message -> acc + message }
 */





