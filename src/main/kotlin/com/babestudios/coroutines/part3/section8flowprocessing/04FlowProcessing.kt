package com.babestudios.coroutines.part3.section8flowprocessing

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

/**
 * We’ve seen quite a few flow processing and lifecycle functions. Their implementation is quite simple, so you can
 * guess that there is no magic going on there. Most such functions can be implemented with flow builder and collect
 * with a lambda. Here is a simple example of flow processing and some simplified map and flowOf implementations:
 */
suspend fun main() {
	flowOf('a', 'b')
		.map { it.uppercase() }
		.collect { print(it) } // AB
}

//fun <T, R> Flow<T>.map(
//	transform: suspend (value: T) -> R
//): Flow<R> = flow {
//	collect { value ->
//		emit(transform(value))
//	}
//}
//
//fun <T> flowOf(vararg elements: T): Flow<T> = flow {
//	for (element in elements) {
//		emit(element)
//	}
//}

/**
 * If you inline filter and map functions, you will end up with the following code (I added labels to the lambdas and
 * comments with numbers).
 */

//suspend fun main() {
//	flow map@{ // 1
//		flow flowOf@{ // 2
//			for (element in arrayOf('a', 'b')) { // 3
//				this@flowOf.emit(element) // 4 }
//			}.collect { value -> // 5 this@map.emit(value.uppercase()) // 6
//			}
//		}.collect { // 7
//			print(it) // 8
//		}
//	}

/**
 * Let’s analyze this step by step. We start a flow at 1 and collect it at 7. When we start collecting, we invoke the
 * lambda @map (which starts at 1), which calls another builder at 2 and collects it at 5. When we collect, we start
 * lambda @flowOn (which starts at 2). This lambda (at 2) iterates over an array with 'a' and 'b'. The first value 'a'
 * is emitted at 4, which calls the lambda at 5. This lambda (at 5) transforms the value to 'A' and emits it to the
 * flow @map, thus calling the lambda at 7. The value is printed; we then finish the lambda at 7 and resume the lambda
 * at 6. It finishes, so we resume @flowOf at 4. We continue the iteration and emit 'b' at 4. So, we call the lambda at
 * 5, transform the value to 'B', and emit it at 6 to the flow @map. The value is collected at 7 and printed at 8. The
 * lambda at 7 is finished, so we resume the lambda at 6. This is finished, so we resume the lambda @flowOf at 4.
 * This is also finished, so we resume the @map on collect at 5. Since there is nothing more, we reach the end of @map.
 * With that, we resume the collect at 7, and we reach the end of the main function.
 *
 * The same happens in most flow processing and lifecycle functions, so understanding this gives us quite a good
 * understanding of how Flow works.
 */