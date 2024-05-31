package com.babestudios.coroutines.part3.section8flowprocessing

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

/**
 * We’ve presented Flow as a pipe through which values flow. As they do so, they can be changed in different ways:
 * dropped, multiplied, transformed, or combined. These operations between flow creation and the terminal operation are
 * called Flow processing. In this chapter, we will learn about the functions we use for this.
 *
 * The functions presented here might remind you of the functions we use for Collection processing. This is no
 * coincidence as they represent the same concepts, with the difference that flow elements can be spread in time.
 *
 * Map
 *
 * The first important function we need to learn about is map, which transforms each flowing element according to its
 * transformation function. So, if you have a flow of numbers and your operation is calculating the squares of these
 * numbers, then the resulting flow will have the squares of these numbers.
 */
suspend fun main() {
	flowOf(1, 2, 3) // [1, 2, 3]
		.map { it * it } // [1, 4, 9]
		.collect { print(it) } // 149
}

/**
 *
 * Most of the Flow processing functions are quite simple to implement with tools we already know from previous
 * chapters. To implement map, we might use the flow builder to create a new flow. Then, we might collect elements from
 * the previous flow and emit each collected element transformed. The implementation below is just a slightly simplified
 * version of the actual one from the kotlinx.coroutines library.
 *
 * fun <T, R> Flow<T>.map(
 * 	transform: suspend (value: T) -> R
 * ): Flow<R> = flow { // here we create a new flow
 * 	collect { value -> // here we collect from receiver
 * 		emit(transform(value))
 * 	}
 * }
 *
 * map is a very popular function. Its use cases include unpacking or converting values into a different type.
 *
 * // Here we use map to have user actions from input events
 * fun actionsFlow(): Flow<UserAction> = observeInputEvents()
 * 	.map { toAction(it.code) }
 *
 * // Here we use map to convert from User to UserJson
 * fun getAllUser(): Flow<UserJson> =
 * 	userRepository.getAllUsers()
 * 		.map { it.toUserJson() }
 */


