package com.babestudios.coroutines.part3.section9sharedflow

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

/**
 * In Kotlin, we like to have a distinction between interfaces that are used to only listen and those that are used to
 * modify. For instance, we’ve already seen the distinction between SendChannel, ReceiveChannel and just Channel.
 * The same rule applies here. MutableSharedFlow inherits from both SharedFlow and FlowCollector. The former inherits
 * from Flow and is used to observe, while FlowCollector is used to emit values.
 *
 * interface MutableSharedFlow<T> : SharedFlow<T>, FlowCollector<T> {
 * 	fun tryEmit(value: T): Boolean
 * 	val subscriptionCount: StateFlow<Int>
 * 	fun resetReplayCache()
 * }
 *
 * interface SharedFlow<out T> : Flow<T> {
 * 	val replayCache: List<T>
 * }
 *
 * interface FlowCollector<in T> {
 * 	suspend fun emit(value: T)
 * }
 *
 * These interfaces are often used to expose only functions, to emit, or only to collect.
 */
suspend fun main(): Unit = coroutineScope {
	val mutableSharedFlow = MutableSharedFlow<String>()
	val sharedFlow: SharedFlow<String> = mutableSharedFlow
	val collector: FlowCollector<String> = mutableSharedFlow
	launch {
		mutableSharedFlow.collect {
			println("#1 received $it")
		}
	}
	launch {
		sharedFlow.collect {
			println("#2 received $it")
		}
	}
	delay(1000)
	mutableSharedFlow.emit("Message1")
	collector.emit("Message2")
}
/**
 * Here is an example of typical usage on Android:
 */
//class UserProfileViewModel {
//	private val _userChanges = MutableSharedFlow<UserChange>()
//	val userChanges: SharedFlow<UserChange> = _userChanges
//
//	fun onCreate() {
//		viewModelScope.launch {
//			userChanges.collect(::applyUserChange)
//		}
//	}
//
//	fun onNameChanged(newName: String) { // ...
//		_userChanges.emit(NameChange(newName))
//	}
//
//	fun onPublicKeyChanged(newPublicKey: String) { // ...
//		_userChanges.emit(PublicKeyChange(newPublicKey))
//	}
//}