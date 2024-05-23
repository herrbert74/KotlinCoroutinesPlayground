package com.babestudios.coroutines.part3.section4flowintroduction

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.withContext

/**
 * This is a case where we should use Flow instead of Sequence. Such an approach fully supports coroutines in its
 * operations. Its builder and operations are suspending functions, and it supports structured concurrency and proper
 * exception handling. We will explain all this in the next chapters, but for now let’s see how it helps with this case.
 *
 * Flow should be used for streams of data that need to use coroutines.
 */
fun getFlow(): Flow<String> = flow {
	repeat(3) {
		delay(1000)
		emit("User$it")
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
		val list = getFlow()
		list.collect { println(it) }
	}
}
// (0.1 sec)
// Processing on coroutine
// (0.1 sec)
// Processing on coroutine
// (0.1 sec)
// Processing on coroutine
// (1 - 3 * 0.1 = 0.7 sec)