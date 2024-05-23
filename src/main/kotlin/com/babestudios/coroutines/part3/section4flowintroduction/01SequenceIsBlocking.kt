package com.babestudios.coroutines.part3.section4flowintroduction

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.withContext

/**
 * I hope you already have some idea that thread blocking can be very dangerous and lead to unexpected situations.
 * To make this crystal clear, take a look at the example below. We use Sequence, so its forEach is a blocking
 * operation.
 * This is why a coroutine started on the same thread with launch will wait, so one coroutine’s execution blocks
 * another’s.
 */
fun getSequence(): Sequence<String> = sequence {
	repeat(3) {
		Thread.sleep(1000)
		// the same result as if there were delay(1000) here
		yield("User$it")
	}
}

suspend fun main() {
	withContext(newSingleThreadContext("main")) {
		launch {
			repeat(3) {
				delay(100)
				println("Processing on coroutine")
			}
		}
		val list = getSequence()
		list.forEach { println(it) }
	}
}