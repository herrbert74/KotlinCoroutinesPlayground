package com.babestudios.coroutines.part2.section6scopefunctions

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * As we’ve already mentioned, coroutineScope is nowadays often used to wrap a suspending main body.
 * You can think of it as the modern replacement for the runBlocking function:
 */
suspend fun main(): Unit = coroutineScope {
	launch {
		delay(1000)
		println("World")
	}
	println("Hello, ")
}
// Hello
// (1 sec)
// World