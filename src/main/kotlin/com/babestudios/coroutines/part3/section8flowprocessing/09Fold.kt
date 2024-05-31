package com.babestudios.coroutines.part3.section8flowprocessing

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.onEach

/**
 * If you use collection processing functions, you might recognize fold. It is used to combine all the values in this
 * collection into one by applying an operation that combines two values into one for each element (starting from the
 * initial value).
 *
 * For example, if the initial value is 0 and the operation is addition, then the result is the sum of all the numbers:
 * we first take the initial value 0; then, we add the first element 1 to it; to the result 1, we add the second number
 * 2; to the result 3, we add the third number 3; to the result 6, we add the last number 4. The result of this
 * operation, 10, is what will be returned from fold.
 */
//fun main() {
//	val list = listOf(1, 2, 3, 4)
//	val res = list.fold(0) { acc, i -> acc + i }
//	println(res) // 10
//	val res2 = list.fold(1) { acc, i -> acc * i }
//	println(res2) // 24
//}

/**
 * fold is a terminal operation. It can also be used for Flow, but it will suspend until this flow is completed
 * (just like collect).
 */
suspend fun main() {
	val list = flowOf(1, 2, 3, 4)
		.onEach { delay(1000) }
	val res = list.fold(0) { acc, i -> acc + i }
	println(res)
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


