package com.babestudios.coroutines.part2.section1builders

import kotlinx.coroutines.*

suspend fun main() {
	coroutineScope {
		val t = System.currentTimeMillis()
		launch {
			//listOf(
				//launch {
					do1()
				//},
				//launch {
					do2()
				//}
			//).joinAll()
		}
	}
}

suspend fun do1() {
	println("Do 1 ${Thread.currentThread().name}")
	delay(1000L)

}

suspend fun do2() {
	println("Do 2 ${Thread.currentThread().name}")
	delay(2000L)

}