package com.babestudios.coroutines.part2.section2coroutinecontext

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * Every element in this set has a unique Key that is used to identify it. These keys are compared by reference.
 * For example CoroutineName or Job implement CoroutineContext.Element, which implements the CoroutineContext interface.
 */
fun main() {
	val name: CoroutineName = CoroutineName("A name")
	val element: CoroutineContext.Element = name
	val context: CoroutineContext = element
	val job: Job = Job()
	val jobElement: CoroutineContext.Element = job
	val jobContext: CoroutineContext = jobElement
}