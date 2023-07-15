package com.babestudios.coroutines.part2.section2coroutinecontext

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext


fun main() {
	val empty: CoroutineContext = EmptyCoroutineContext
	println(empty[CoroutineName]) // null
	println(empty[Job]) // null
	val ctxName = empty + CoroutineName("Name1") + empty
	println(ctxName[CoroutineName]) // CoroutineName(Name1)
}