package com.babestudios.coroutines.part3.section7lifecyclefunctions

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

/**
 * How does this (PREVIOUS) code work? The terminal operation call requests elements from upstream, thereby propagating
 * the coroutine context. However, it can also be modified by the flowOn function.
 *
 * Remember that flowOn works only for functions that are upstream in the flow (PICTURE).
 */
suspend fun present(place: String, message: String) {
	val ctx = coroutineContext
	val name = ctx[CoroutineName]?.name
	println("[$name] $message on $place")
}

fun messagesFlow(): Flow<String> = flow {
	present("flow builder", "Message")
	emit("Message")
}

suspend fun main() {
	val users = messagesFlow()
	withContext(CoroutineName("Name1")) {
		users
			.flowOn(CoroutineName("Name3"))
			.onEach { present("onEach", it) }
			.flowOn(CoroutineName("Name2"))
			.collect { present("collect", it) }
	}
}