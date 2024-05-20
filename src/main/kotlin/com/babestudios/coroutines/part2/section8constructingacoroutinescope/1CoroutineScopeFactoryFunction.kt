package com.babestudios.coroutines.part2.section8constructingacoroutinescope

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

fun CoroutineScope(
	context: CoroutineContext
): CoroutineScope =
	ContextScope(
		if (context[Job] != null) context
		else context + Job()
	)

internal class ContextScope(
	context: CoroutineContext
) : CoroutineScope {
	override val coroutineContext: CoroutineContext = context
	override fun toString(): String =
		"CoroutineScope(coroutineContext=$coroutineContext)"
}
