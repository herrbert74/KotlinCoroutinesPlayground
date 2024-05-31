package com.babestudios.coroutines.part3.section8flowprocessing

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

/**
 * // Simplified distinctUntilChanged implementation
 * fun <T> Flow<T>.distinctUntilChanged(): Flow<T> = flow {
 * 	var previous: Any? = NOT_SET
 * 	collect {
 * 		if (previous == NOT_SET || previous != it) {
 * 			emit(it)
 * 			previous = it
 * 		}
 * 	}
 * }
 *
 * private val NOT_SET = Any()
 *
 * Another function I find very useful is distinctUntilChanged, which helps us eliminate repeating elements that are
 * considered identical. Note that this function only eliminates elements that are identical to the corresponding
 * previous element.
 */
suspend fun main() {
	flowOf(1, 2, 2, 3, 2, 1, 1, 3)
		.distinctUntilChanged()
		.collect { print(it) } // 123213
}
