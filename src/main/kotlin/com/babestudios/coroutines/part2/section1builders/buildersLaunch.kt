package com.babestudios.coroutines.part2.section1builders

import kotlinx.coroutines.*

suspend fun main() {
	coroutineScope {
		val t = System.currentTimeMillis()
		launch {
			listOf(
				launch {
					println("Start 1 ${System.currentTimeMillis() - t}")
					delay(1000L)
					println("Finish 1 ${System.currentTimeMillis() - t}")
				},
				launch {
					println("Start 2 ${System.currentTimeMillis() - t}")
					delay(1000L)
					println("Finish 2 ${System.currentTimeMillis() - t}")
				}
			).joinAll()
		}
	}
}
