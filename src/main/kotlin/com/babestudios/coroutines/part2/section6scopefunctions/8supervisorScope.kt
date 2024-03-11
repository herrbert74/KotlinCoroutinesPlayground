package com.babestudios.coroutines.part2.section6scopefunctions

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope

/**
 * The supervisorScope function also behaves a lot like coroutineScope:
 * it creates a CoroutineScope that inherits from the outer scope and calls the specified suspend block in it.
 * The difference is that it overrides the context’s Job with SupervisorJob,
 * so it is not cancelled when a child raises an exception.
 */
fun main() = runBlocking {
	println("Before")
	supervisorScope {
		launch {
			delay(1000)
			throw Error()
		}
		launch {
			delay(2000)
			println("Done")
		}
	}
	println("After")
}
// Before
// (1 sec)
// Exception...
// (1 sec)
// Done
// After
