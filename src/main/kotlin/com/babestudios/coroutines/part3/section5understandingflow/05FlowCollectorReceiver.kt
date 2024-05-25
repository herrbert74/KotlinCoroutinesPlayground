package com.babestudios.coroutines.part3.section5understandingflow

/**
 * Calling emit on it is not convenient; instead, we’ll make FlowCollector a receiver.
 * Thanks to that, inside our lambda expression there is a receiver (this keyword) of type FlowCollector.
 * This means we can call this.emit or just emit. The f invocation still stays the same.
 *
 */
suspend fun main() {
	val f: suspend FlowCollector3.() -> Unit = {
		emit("A")
		emit("B")
		emit("C")
	}
	f { print(it) } // ABC
	f { print(it) } // ABC
}

