package com.babestudios.coroutines.part4addendum

import com.babestudios.coroutines.part3.section8flowprocessing.isEven
import kotlinx.coroutines.flow.all
import kotlinx.coroutines.flow.any
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.none

/**
 * When flows were introduced, the first and simplest function was a filter. It looked like this:
 */
//suspend fun main() {
//	(1..10).asFlow() // [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
//		.filter { it <= 5 } // [1, 2, 3, 4, 5]
//		.filter { isEven(it) } // [2, 4]
//		.collect { print(it) } // 24
//}

/**
 * In coroutines 1.10.0, terminal operators Flow.any(), Flow.all() or Flow.none() were added, which are not to be
 * confused with the above. They return true if there is a match and false if there is no match.
 * See https://github.com/Kotlin/kotlinx.coroutines/releases/tag/1.10.0
 */
suspend fun main() {
	val resultAny = (1..10).asFlow().any { it <= 5 && isEven(it) } // true
	val resultAll = (1..10).asFlow().all { it <= 5 && isEven(it) } // false
	val resultNone = (1..10).asFlow().none { it <= 5 && isEven(it) } // false
	print("$resultAny $resultAll $resultNone")
}