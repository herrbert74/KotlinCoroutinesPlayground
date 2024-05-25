package com.babestudios.coroutines.part3.section5understandingflow

/**
 * The fact is that emit should also be a suspending function. Our function type is already getting quite complex,
 * so we’ll simplify it by defining a FlowCollector function interface with an abstract method named emit.
 * We will use this interface instead of the function type. The trick is that functional interfaces can be defined
 * with lambda expressions, therefore we don’t need to change the f call.
 */
fun interface FlowCollector3 {
	suspend fun emit(value: String)
}

suspend fun main() {
	val f: suspend (FlowCollector3) -> Unit = {
		it.emit("A")
		it.emit("B")
		it.emit("C")
	}
	f { print(it) } // ABC
	f { print(it) } // ABC
}
