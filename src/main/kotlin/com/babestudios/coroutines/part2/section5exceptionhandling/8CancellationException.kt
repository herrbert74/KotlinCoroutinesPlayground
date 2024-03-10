package com.babestudios.coroutines.part2.section5exceptionhandling


import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

@Suppress("JavaIoSerializableObjectMustHaveReadResolve")
object MyNonPropagatingException : CancellationException()

/**
 * If an exception is a subclass of CancellationException, it will not be propagated to its parent.
 * It will only cause cancellation of the current coroutine.
 * CancellationException is an open class, so it can be extended by our own classes or objects.
 *
 * In the below snippet, we start two coroutines with builders at 1 and 4.
 * At 3, we throw a MyNonPropagatingException exception, which is a subtype of CancellationException.
 * This exception is caught by launch (started at 1).
 * This builder cancels itself, then it also cancels its children, namely the builder defined at 2.
 * The launch started at 4 is not affected, so it prints “Will be printed” after 2 seconds.
 */
suspend fun main(): Unit = coroutineScope {
	launch { // 1
		launch { // 2
			delay(2000)
			println("Will not be printed")
		}
		throw MyNonPropagatingException // 3
	}
	launch { // 4
		delay(2000)
		println("Will be printed")
	}
}
// (2 sec)
// Will be printed
