package com.babestudios.coroutines.part3.section7lifecyclefunctions

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

/**
 * Lambda expressions used as arguments for flow operations (like onEach, onStart, onCompletion, etc.) and its builders
 * (like flow or channelFlow) are all suspending in nature. Suspending functions need to have a context and should be
 * in relation to their parent (for structured concurrency). So, you might be wondering where these functions take their
 * context from. The answer is: from the context where collect is called.
 */
fun usersFlow(): Flow<String> = flow {
	repeat(2) {
		val ctx = currentCoroutineContext()
		val name = ctx[CoroutineName]?.name
		emit("User$it in $name")
	}
}

suspend fun main() {
	val users = usersFlow()
	withContext(CoroutineName("Name1")) {
		users.collect { println(it) }
	}
	withContext(CoroutineName("Name2")) {
		users.collect { println(it) }
	}
}
