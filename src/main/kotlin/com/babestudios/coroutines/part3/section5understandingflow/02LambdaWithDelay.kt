package com.babestudios.coroutines.part3.section5understandingflow

import kotlinx.coroutines.delay

/**
 * To make it a bit spicier, let’s make our lambda expression suspend and add some delay inside it.
 * Notice that each call of such a lambda expression is sequential, so you shouldn’t make another call until
 * the previous one is finished.
 */
suspend fun main() {
	val f: suspend () -> Unit = {
		print("A")
		delay(1000)
		print("B")
		delay(1000)
		print("C")
	}
	f() // ABC
	f() // ABC
}
