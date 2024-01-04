package com.babestudios.coroutines.part2.section2coroutinecontext

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun CoroutineScope.log(msg: String) {
	val name = coroutineContext[CoroutineName]?.name
	println("[$name] $msg")
}

fun main() = runBlocking(CoroutineName("main")) {
	log("Started") // [main] Started
	val v1 = async {
		delay(500)
		log("Running async") // [main] Running async
		42
	}
	launch {
		delay(1000)
		log("Running launch") // [main] Running launch
	}
	log("The answer is ${v1.await()}")
	// [main] The answer is 42
}
