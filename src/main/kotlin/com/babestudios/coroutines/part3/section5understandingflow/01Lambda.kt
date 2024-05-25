package com.babestudios.coroutines.part3.section5understandingflow

/**
 * We’ll start our story with a simple lambda expression. Each lambda expression
 * can be defined once and then called multiple times.
 */
fun main() {
	val f: () -> Unit = {
		print("A")
		print("B")
		print("C")
	}
	f() // ABC
	f() // ABC
}
