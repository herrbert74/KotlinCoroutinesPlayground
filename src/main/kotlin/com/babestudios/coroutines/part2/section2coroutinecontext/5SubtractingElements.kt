package com.babestudios.coroutines.part2.section2coroutinecontext

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Job

/**
 * Unlike the plus operator, he minus operator is NOT overloaded for CoroutineContext.
 */
fun main() {
	val ctx = CoroutineName("Name1") + Job()
	println(ctx[CoroutineName]?.name) // Name1
	println(ctx[Job]?.isActive) // true
	val ctx2 = ctx.minusKey(CoroutineName)
	println(ctx2[CoroutineName]?.name) // null
	println(ctx2[Job]?.isActive) // true
	val ctx3 = (ctx + CoroutineName("Name2")).minusKey(CoroutineName)
	println(ctx3[CoroutineName]?.name) // null
	println(ctx3[Job]?.isActive) // true
}