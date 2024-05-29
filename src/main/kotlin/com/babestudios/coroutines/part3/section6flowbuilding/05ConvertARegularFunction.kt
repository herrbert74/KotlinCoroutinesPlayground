package com.babestudios.coroutines.part3.section6flowbuilding

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow

/**
 * To convert a regular function, we need to reference it first. We do this using :: in Kotlin.
 */
suspend fun getUserName(): String {
	delay(1000)
	return "UserName"
}

suspend fun main() {
	::getUserName.asFlow()
		.collect { println(it) }
}