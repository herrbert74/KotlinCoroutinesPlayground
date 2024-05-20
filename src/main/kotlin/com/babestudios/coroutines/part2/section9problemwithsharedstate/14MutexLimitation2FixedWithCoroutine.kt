package com.babestudios.coroutines.part2.section9problemwithsharedstate

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlin.system.measureTimeMillis

/**
 * When we use a dispatcher that is limited to a single thread, we don’t have such a problem. When a delay or a
 * network call suspends a coroutine, the thread can be used by other coroutines.
 */
class MessagesRepository2 {
	private val messages = mutableListOf<String>()
	private val dispatcher = Dispatchers.IO
		.limitedParallelism(1)

	suspend fun add(message: String) =
		withContext(dispatcher) {
			delay(1000) // we simulate network call
			messages.add(message)
		}
}

suspend fun main() {
	val repo = MessagesRepository2()

	val timeMillis = measureTimeMillis {
		coroutineScope {
			repeat(5) {
				launch {
					repo.add("Message$it")
				}
			}
		}
	}
	println(timeMillis) // 1058
}