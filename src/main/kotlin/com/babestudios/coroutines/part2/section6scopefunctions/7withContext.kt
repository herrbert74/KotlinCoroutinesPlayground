package com.babestudios.coroutines.part2.section6scopefunctions

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

/**
 * The withContext function is similar to coroutineScope,
 * but it additionally allows some changes to be made to the scope.
 * The context provided as an argument to this function overrides the context from the parent scope
 * (the same way as in coroutine builders).
 * This means that withContext(EmptyCoroutineContext) and coroutineScope() behave in exactly the same way.
 */
fun CoroutineScope.log(text: String) {
	val name = this.coroutineContext[CoroutineName]?.name
	println("[$name] $text")
}

fun main() = runBlocking(CoroutineName("Parent")) {
	log("Before")
	withContext(CoroutineName("Child 1")) {
		delay(1000)
		log("Hello 1")
	}
	withContext(CoroutineName("Child 2")) {
		delay(1000)
		log("Hello 2")
	}
	log("After")
}
// [Parent] Before
// (1 sec)
// [Child 1] Hello 1
// (1 sec)
// [Child 2] Hello 2
// [Parent] After
