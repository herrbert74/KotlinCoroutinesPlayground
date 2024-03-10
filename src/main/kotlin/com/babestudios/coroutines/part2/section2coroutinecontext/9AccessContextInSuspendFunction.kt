package com.babestudios.coroutines.part2.section2coroutinecontext

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

suspend fun printName() {
	println(coroutineContext[CoroutineName]?.name)
	println(coroutineContext[Job])
}

/**
 * We can access parent context like this
 */
suspend fun main() = withContext(CoroutineName("Outer")) {
	printName() // Outer
	launch(CoroutineName("Inner")) {
		printName() // Inner
	}
	delay(10)
	printName() // Outer
}