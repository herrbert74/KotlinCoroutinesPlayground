package com.babestudios.coroutines.part3.section5understandingflow

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import kotlin.random.Random

/**
 * When you implement more complex algorithms for flow processing, you should know when you need to synchronize
 * access to variables. Let’s analyze the most important use cases. When you implement some custom flow processing
 * functions, you can define mutable states inside the flow without any mechanism for synchronization because a
 * flow step is synchronous by nature.
 */
fun <T, K> kotlinx.coroutines.flow.Flow<T>.distinctBy(
	keySelector: (T) -> K
) = flow {
	val sentKeys = mutableSetOf<K>()
	collect { value ->
		val key = keySelector(value)
		if (key !in sentKeys) {
			sentKeys.add(key)
			emit(value)
		}
	}
}

/**
 * Here is an example that is used inside a flow step and produces consistent results; the counter variable is always
 * incremented to 1000.
 */
fun kotlinx.coroutines.flow.Flow<*>.counter() = kotlinx.coroutines.flow.flow<Int> {
	var counter = 0
	collect {
		counter++
		// to make it busy for a while
		List(100) {
			Random.nextLong()
		}.shuffled().sorted()
		emit(counter)
	}
}

suspend fun main(): Unit = coroutineScope {
	val f1 = List(1000) { "$it" }.asFlow()
	val f2 = List(1000) { "$it" }.asFlow()
		.counter()

	launch { println(f1.counter().last()) } // 1000
	launch { println(f1.counter().last()) } // 1000
	launch { println(f2.last()) } // 1000
	launch { println(f2.last()) } // 1000
}