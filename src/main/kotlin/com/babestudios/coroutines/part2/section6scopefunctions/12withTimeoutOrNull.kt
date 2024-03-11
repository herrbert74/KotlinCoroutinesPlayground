package com.babestudios.coroutines.part2.section6scopefunctions

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withTimeoutOrNull
import kotlinx.coroutines.yield

/**
 * A less aggressive variant of withTimeout is withTimeoutOrNull, which does not throw an exception.
 * If the timeout is exceeded, it just cancels its body and returns null.
 * I find withTimeoutOrNull useful for wrapping functions in which waiting times that are too long signal that
 * something went wrong. For instance, network operations: if we wait over 5 seconds for a response, it is unlikely
 * we will ever receive it (some libraries might wait forever).
 */
data class User(val name: String)

suspend fun fetchUser(): User { // Runs forever
	while (true) {
		yield()
	}
}

suspend fun getUserOrNull(): User? = withTimeoutOrNull(5000) {
	fetchUser()
}

suspend fun main(): Unit = coroutineScope {
	val user = getUserOrNull()
	println("User: $user")
}
// (5 sec)
// User: null

