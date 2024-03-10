package com.babestudios.coroutines.part2.section5exceptionhandling

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope

/**
 * So, we know how to stop exception propagation, but sometimes this is not enough.
 * In the case of an exception, the async coroutine builder breaks its parent,
 * just like launch and other coroutine builders that have a relation with their parents.
 * However, what if this process is silenced (for instance, using SupervisorJob or supervisorScope) and await is called?
 * Let’s look at the following example:
 *
 * We have no value to return since the coroutine ended with an exception,
 * so instead the MyException exception is thrown by await. This is why MyException is printed.
 * The other async finishes uninterrupted because we’re using the supervisorScope.
 */
class MyException : Throwable()

suspend fun main() = supervisorScope {
	val str1 = async<String> {
		delay(1000)
		throw MyException()
	}
	val str2 = async {
		delay(2000)
		"Text2"
	}
	try {
		println(str1.await())
	} catch (e: MyException) {
		println(e)
	}
	println(str2.await())
}
// MyException
// Text2