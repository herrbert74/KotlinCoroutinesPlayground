package com.babestudios.coroutines.part2.section1builders

import kotlinx.coroutines.*

suspend fun main() {
	coroutineScope {
		val t = System.currentTimeMillis()
		val res1 = async {
			println("Start 1 ${System.currentTimeMillis() - t}")
			delay(1000L)
			"Finish 1 ${System.currentTimeMillis() - t}"
		}
		val res2 = async {
			println("Start 2 ${System.currentTimeMillis() - t}")
			delay(3000L)
			"Finish 2 ${System.currentTimeMillis() - t}"
		}
		val res3 = async {
			println("Start 3 ${System.currentTimeMillis() - t}")
			delay(2000L)
			"Finish 3 ${System.currentTimeMillis() - t}"
		}
		println(res1.await() )
		println("${System.currentTimeMillis() - t}")
		println(res2.await())
		println("${System.currentTimeMillis() - t}")
		println(res3.await())
		println("${System.currentTimeMillis() - t}")
	}
}