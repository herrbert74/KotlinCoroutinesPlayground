package com.babestudios.coroutines.part2.section2coroutinecontext

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * When two elements with different keys are added, the resulting context responds to both keys.
 * When another element with the same key is added, just like in a map, the new element replaces the previous one.
 */
fun main() {
	val ctx: CoroutineContext = CoroutineName("Name0")
	println(ctx[CoroutineName]?.name) // Name0
	println(ctx[Job]?.isActive) // null
	val ctx1: CoroutineContext = CoroutineName("Name1")
	println(ctx1[CoroutineName]?.name) // Name1
	println(ctx1[Job]?.isActive) // null
	val ctx2: CoroutineContext = Job()
	println(ctx2[CoroutineName]?.name) // null
	println(ctx2[Job]?.isActive) // true, because "Active" is the default state of a job created this way
	val ctx3 = ctx1 + ctx2
	println(ctx3[CoroutineName]?.name) // Name1
	println(ctx3[Job]?.isActive) // true
	val ctx4 = ctx1 + ctx
	println(ctx4[CoroutineName]?.name) // Name0
	println(ctx4[Job]?.isActive) // true
}