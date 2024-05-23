package com.babestudios.coroutines.part3.section4flowintroduction

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.withContext

/**
 * Flow’s terminal operations (like collect) suspend a coroutine instead of blocking a thread.
 * They also support other coroutine functionalities, such as respecting the coroutine context and handling exceptions.
 * Flow processing can be cancelled, and structured concurrency is supported out of the box. The flow builder is not
 * suspending and does not require any scope. It is the terminal operation that is suspending and builds a relation to
 * its parent coroutine (similar to the coroutineScope function).
 * The below example shows how CoroutineName context is passed from collect to the lambda expression in the flow
 * builder. It also shows that launch cancellation also leads to proper flow processing cancellation.
 */

//Notice, that this function is not suspending and does not need CoroutineScope
fun usersFlow(): Flow<String> = flow {
	repeat(3) {
		delay(1000)
		val ctx = currentCoroutineContext()
		val name = ctx[CoroutineName]?.name
		emit("User$it in $name")
	}
}

suspend fun main() {
	val users = usersFlow()
	withContext(CoroutineName("Name")) {
		val job = launch {

			// collect is suspending
			users.collect { println(it) }
		}
		launch {
			delay(2100)
			println("I got enough")
			job.cancel()
		}
	}
}
// (1 sec)
// User0 in Name
// (1 sec)
// User1 in Name
// (0.1 sec)
// I got enough
