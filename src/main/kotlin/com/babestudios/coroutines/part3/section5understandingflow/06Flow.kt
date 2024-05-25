package com.babestudios.coroutines.part3.section5understandingflow

/**
 * Instead of passing around lambda expressions, we prefer to have an object implementing an interface.
 * We will call this interface Flow and wrap our definition with an object expression.
 */
interface Flow3 {
	suspend fun collect(collector: FlowCollector3)
}

suspend fun main() {

	val builder: suspend FlowCollector3.() -> Unit = {
		emit("A")
		emit("B")
		emit("C")
	}

	val flow3: Flow3 = object : Flow3 {
		override suspend fun collect(collector: FlowCollector3) {
			collector.builder()
		}
	}
	flow3.collect { print(it) } // ABC
	flow3.collect { print(it) } // ABC
}

