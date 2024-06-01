package com.babestudios.coroutines.part3.section9sharedflow

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

/**
 * StateFlow is an extension of the SharedFlow concept. It works similarly to SharedFlow when the replay parameter is
 * set to 1. It always stores one value, which can be accessed using the value property.
 */

//interface StateFlow<out T> : SharedFlow<T> {
//	val value: T
//}
//
//interface MutableStateFlow<T> :
//	StateFlow<T>, MutableSharedFlow<T> {
//	override var value: T
//	fun compareAndSet(expect: T, update: T): Boolean
//}

/**
 * Please note how the value property is overridden inside MutableStateFlow. In Kotlin, an open val property can be
 * overridden with a var property. val only allows getting a value (getter), while var also supports setting a new
 * value (setter).
 *
 * The initial value needs to be passed to the constructor. We both access and set the value using the value property.
 * As you can see, MutableStateFlow is like an observable holder for a value.
 */
suspend fun main() = coroutineScope {
	val state = MutableStateFlow("A")
	println(state.value) // A
	launch {
		state.collect { println("Value changed to $it") }
		// Value changed to A
	}
	delay(1000)
	state.value = "B" // Value changed to B
	delay(1000)
	launch {
		state.collect { println("and now it is $it") }
		// and now it is B
	}
	delay(1000)
	state.value = "C" // Value changed to C and now it is C
}

/**
 * On Android, StateFlow is used as a modern alternative to LiveData. First, it has full support for coroutines. Second,
 * it has an initial value, so it does not need to be nullable. So, StateFlow is often used on ViewModels to represent
 * its state. This state is observed, and a view is displayed and updated on this basis.
 */
//class LatestNewsViewModel(
//	private val newsRepository: NewsRepository
//) : ViewModel() {
//	private val _uiState = MutableStateFlow<NewsState>(LoadingNews)
//	val uiState: StateFlow<NewsState> = _uiState
//	fun onCreate() {
//		scope.launch {
//			_uiState.value =
//				NewsLoaded(newsRepository.getNews())
//		}
//	}
//}