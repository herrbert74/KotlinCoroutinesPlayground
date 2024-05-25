package com.babestudios.coroutines.part3.section5understandingflow

import kotlinx.coroutines.delay

/**
 * A lambda expression might have a parameter that can represent a function. We will call this parameter emit.
 * So, when you call the lambda expression f, you need to specify another lambda expression that will be used as emit.
 */
suspend fun main() {
	val f: suspend ((String) -> Unit) -> Unit = { emit ->
		emit("A")
		emit("B")
		emit("C")
	}
	f { print(it) } // ABC
	f { print(it) } // ABC
}
