@file:OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)

package com.babestudios.coroutines.part3.section9sharedflow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.setMain
import java.util.concurrent.Executors
import kotlin.random.Random

private val dispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()

fun randomSharedFlow(): SharedFlow<Int> {
	val sharedFlow = MutableSharedFlow<Int>(replay = 2)

	GlobalScope.launch(Dispatchers.Default) {
		for (i in 0 until 40) {
			//sharedFlow.emit(Random.nextInt(1, 100))
			delay(40)
		}
	}

	return sharedFlow
}

fun main() {
	Dispatchers.setMain(dispatcher)
	val sharedFlow = randomSharedFlow()

	GlobalScope.launch(Dispatchers.Main) {
		sharedFlow.collect { println("Collector A: $it") }
		println("That's all folks!")
	}

	GlobalScope.launch(Dispatchers.Main) {
		delay(1000)
		println("Single: ${sharedFlow.replayCache.firstOrNull()}")
		println("First: ${sharedFlow.firstOrNull()}")
		delay(30)
		println("First: ${sharedFlow.firstOrNull()}")
		println("Single: ${sharedFlow.replayCache}")
		println("Last: ${sharedFlow.lastOrNull()}")
		sharedFlow.collect { println("Collector B: $it") }
		println("That's all folks!")
	}

	println("...and we're off!")
}
