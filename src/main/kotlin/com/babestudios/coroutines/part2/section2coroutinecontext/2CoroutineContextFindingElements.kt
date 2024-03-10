package com.babestudios.coroutines.part2.section2coroutinecontext

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * Since CoroutineContext is like a collection, we can find an element with a concrete key using get.
 * Another option is to use square brackets, because in Kotlin the get method is an operator and can be invoked using
 * square brackets instead of an explicit function call. Just like in Map: when an element is in the context,
 * it will be returned. If it is not, null will be returned instead.
 */
fun main() {
	val ctx: CoroutineContext = CoroutineName("A name")
	val coroutineName: CoroutineName? = ctx[CoroutineName] // or ctx.get(CoroutineName)
	println(coroutineName?.name) // A name
	val job: Job? = ctx[Job] // or ctx.get(Job)
	println(job) // null
}