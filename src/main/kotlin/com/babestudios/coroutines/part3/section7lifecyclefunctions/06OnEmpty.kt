package com.babestudios.coroutines.part3.section7lifecyclefunctions

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEmpty

/**
 * A flow might complete without emitting any value, which might be an indication of an unexpected event. For such
 * cases, there is the onEmpty function, which invokes the given action when this flow completes without emitting any
 * elements. onEmpty might then be used to emit some default value.
 */
suspend fun main() = coroutineScope {
	flow<List<Int>> { delay(1000) }
		.onEmpty { emit(emptyList()) }
		.collect { println(it) }
}

