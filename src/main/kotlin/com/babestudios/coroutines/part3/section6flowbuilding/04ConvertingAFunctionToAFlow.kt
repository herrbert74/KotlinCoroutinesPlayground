package com.babestudios.coroutines.part3.section6flowbuilding

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow

/**
 * Flow is frequently used to represent a single value delayed in time (like a Single in RxJava).
 * So, it makes sense to convert a suspending function into a flow.
 * The result of this function will be the only value in this flow.
 * For that, there is the asFlow extension function, which works on function types(both suspend () -> T and() -> T).
 * Here it is used to convert a suspending lambda expression into Flow.
 */
suspend fun main() {
	val function = suspend {
		// this is suspending lambda expression
		delay(1000)
		"UserName"
	}
	function.asFlow()
		.collect {
			println(it)
		}
}
// (1 sec)
// UserName