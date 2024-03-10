package com.babestudios.coroutines.part2.section3jobsandawaitingchildren

import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * The Job interface also exposes a children property that lets us reference all its children.
 * We might as well use it to wait until all children are in a final state.
 */
fun main(): Unit = runBlocking {
	launch {
		delay(1000)
		println("Test1")
	}
	launch {
		delay(2000)
		println("Test2")
	}
	val children = coroutineContext[Job]?.children
	val childrenNum = children?.count()
	println("Number of children: $childrenNum")
	children?.forEach { it.join() }
	println("All tests are done")
}
// Number of children: 2
// (1 sec)
// Test1
// (1 sec)
// Test2
// All tests are done