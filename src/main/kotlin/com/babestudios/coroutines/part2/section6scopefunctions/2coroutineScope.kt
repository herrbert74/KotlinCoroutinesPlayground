package com.babestudios.coroutines.part2.section6scopefunctions

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

/**
 * coroutineScope is a suspending function that starts a scope. It returns the value produced by the argument function.
 *
 * suspend fun <R> coroutineScope(
 * 	block: suspend CoroutineScope.() -> R
 * ): R
 *
 * Unlike async or launch, the body of coroutineScope is called in-place. It formally creates a new coroutine,
 * but it suspends the previous one until the new one is finished, so it does not start any concurrent process.
 * Take a look at the below example, in which both delay calls suspend runBlocking.
 *
 * The provided scope inherits its coroutineContext from the outer scope, but it overrides the context’s Job.
 * Thus, the produced scope respects its parental responsibilities:
 * • inherits a context from its parent;
 * • waits for all its children before it can finish itself;
 * • cancels all its children when the parent is cancelled.
 * In the example below, you can observe that “After” will be printed at the end because coroutineScope will not finish until all its children are finished. Also, CoroutineName is properly passed from parent to child.
 */
fun main() = runBlocking {
	val a = coroutineScope {
		delay(1000)
		10
	}
	println("a is calculated")
	val b = coroutineScope {
		delay(1000)
		20
	}
	println(a) // 10
	println(b) // 20
}
// (1 sec)
// a is calculated
// (1 sec)
// 10
// 20