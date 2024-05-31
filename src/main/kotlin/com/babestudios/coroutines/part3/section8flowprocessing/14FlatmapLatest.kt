package com.babestudios.coroutines.part3.section8flowprocessing

import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf

/**
 * The last function is flatMapLatest. It forgets about the previous flow once a new one appears. With every new value,
 * the previous flow processing is forgotten. So, if there is no delay between “A”, “B” and “C”, then you will only see
 * “1_C”, “2_C”, and “3_C”.
 */
suspend fun main() {
	flowOf("A", "B", "C")
		.flatMapLatest { flowFrom(it) }
		.collect { println(it) }
}
