package com.babestudios.coroutines.part2.section2coroutinecontext

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking(CoroutineName("main")) {
	log("Started") // [main] Started
	val v1 = async(CoroutineName("c1")) {
		delay(500)
		log("Running async") // [c1] Running async
		42
	}
	launch(CoroutineName("c2")) {
		delay(1000)
		log("Running launch") // [c2] Running launch
	}
	log("The answer is ${v1.await()}")
	// [main] The answer is 42
}