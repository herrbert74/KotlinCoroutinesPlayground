package com.babestudios.coroutines.part2.section9problemwithsharedstate

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.system.measureTimeMillis

/**
 * The second problem with mutex is that it is not unlocked when a coroutine is suspended.
 * Take a look at the code below. It takes over 5 seconds because mutex is still locked during delay.
 */
class MessagesRepository {

	private val messages = mutableListOf<String>()
	private val mutex = Mutex()

	suspend fun add(message: String) = mutex.withLock {
		delay(1000) // we simulate network call
		messages.add(message)
	}

}

suspend fun main() {
	val repo = MessagesRepository()
	val timeMillis = measureTimeMillis {
		coroutineScope {
			repeat(5) {
				launch {
					repo.add("Message $it")
				}
			}
		}
	}
	println(timeMillis) // ~5120
}
