package com.babestudios.coroutines.part3.section7lifecyclefunctions

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

/**
 * collect is a suspending operation that suspends a coroutine until the flow is completed. It is common to wrap it
 * with a launch builder so that flow processing can start on another coroutine. To help with such cases, there is the
 * launchIn function, which launches collect in a new coroutine on the scope object passed as the only argument.
 *
 * fun <T> Flow<T>.launchIn(scope: CoroutineScope): Job = scope.launch { collect() }
 *
 * launchIn is often used to start flow processing in a separate coroutine.
 */
suspend fun main(): Unit = coroutineScope {
	flowOf("User1", "User2")
		.onStart { println("Users:") }
		.onEach { println(it) }
		.launchIn(this)
}