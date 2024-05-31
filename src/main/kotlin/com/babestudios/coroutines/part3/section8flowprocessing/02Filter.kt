package com.babestudios.coroutines.part3.section8flowprocessing

import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filter

/**
 * The next important function is filter, which returns a flow containing only values from the original flow that match
 * the given predicate.
 */
suspend fun main() {
	(1..10).asFlow() // [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
		.filter { it <= 5 } // [1, 2, 3, 4, 5]
		.filter { isEven(it) } // [2, 4]
		.collect { print(it) } // 24
}

fun isEven(num: Int): Boolean = num % 2 == 0

/**
 *
 * This function can also be implemented quite easily using the flow builder. We would just need to introduce an if
 * statement with the predicate (instead of transformation).
 *
 * fun <T> Flow<T>.filter(
 * 	predicate: suspend (T) -> Boolean
 * ): Flow<T> = flow { // here we create a new flow
 * 	collect { value -> // here we collect from receiver
 * 		if (predicate(value)) {
 * 			emit(value)
 * 		}
 * 	}
 * }
 *
 * filter is typically used to eliminate elements we are not interested in.
 *
 * // Here we use filter to drop invalid actions
 * fun actionsFlow(): Flow<UserAction> = observeInputEvents()
 * 	.filter { isValidAction(it.code) }
 * 	.map { toAction(it.code) }
 */



