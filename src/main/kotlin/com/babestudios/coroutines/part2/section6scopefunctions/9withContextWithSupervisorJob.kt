package com.babestudios.coroutines.part2.section6scopefunctions

import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

/**
 * In my workshops, I am often asked if we can use withContext(SupervisorJob()) instead of supervisorScope.
 * No, we can’t. When we use withContext(SupervisorJob()), then withContext is still using a regular Job,
 * and the SupervisorJob() becomes its parent. As a result, when one child raises an exception,
 * the other children will be cancelled as well. withContext will also throw an exception,
 * so its SupervisorJob() is practically useless. This is why I find withContext(SupervisorJob()) pointless and
 * misleading, and I consider it a bad practice.
 */
fun main() = runBlocking {
	println("Before")
	withContext(SupervisorJob()) {
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
