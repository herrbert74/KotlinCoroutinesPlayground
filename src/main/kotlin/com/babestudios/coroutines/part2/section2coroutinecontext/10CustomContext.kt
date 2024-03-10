package com.babestudios.coroutines.part2.section2coroutinecontext

import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

/**
 * we can create our own coroutine context pretty easily.
 * To do this, the easiest way is to create a class that implements the CoroutineContext.Element interface.
 * Such a class needs a property key of type CoroutineContext.Key<*>.
 * This key will be used as the key that identifies this context.
 * The common practice is to use this class’s companion object as a key.
 */
class CounterContext(private val name: String) : CoroutineContext.Element {

	override val key: CoroutineContext.Key<*> = Key
	private var nextNumber = 0

	fun printNext() {
		println("$name: $nextNumber")
		nextNumber++
	}

	companion object Key : CoroutineContext.Key<CounterContext>

}

suspend fun printNext() {
	coroutineContext[CounterContext]?.printNext()
}

suspend fun main(): Unit = withContext(CounterContext("Outer")) {
	printNext() // Outer: 0
	launch {
		printNext() // Outer: 1
		launch {
			printNext() // Outer: 2
		}
		launch(CounterContext("Inner")) {
			printNext() // Inner: 0
			printNext() // Inner: 1
			launch {
				printNext() // Inner: 2
			}
		}
	}
	printNext() // Outer: 3
}