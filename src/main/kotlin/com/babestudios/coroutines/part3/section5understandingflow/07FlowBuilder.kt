package com.babestudios.coroutines.part3.section5understandingflow

/**
 * Finally, let’s define the flow builder function to simplify our flow creation.
 */
fun flow3(
	builder: suspend FlowCollector3.() -> Unit
) = object : Flow3 {
	override suspend fun collect(collector: FlowCollector3) {
		collector.builder()
	}
}

suspend fun main() {
	val f: Flow3 = flow3 {
		emit("A")
		emit("B")
		emit("C")
	}
	f.collect { print(it) } // ABC
	f.collect { print(it) } // ABC
}
