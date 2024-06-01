package com.babestudios.coroutines.part3.section9sharedflow

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn

/**
 * The second variant of stateIn is not suspending, but it requires an initial value and a started mode. This mode has
 * the same options as shareIn (as previously explained).
 */
suspend fun main() = coroutineScope {
	val flow = flowOf("A", "B").onEach { delay(1000) }
		.onEach { println("Produced $it") }
	val stateFlow: StateFlow<String> = flow.stateIn(
		scope = this,
		started = SharingStarted.Lazily,
		initialValue = "Empty"
	)
	println(stateFlow.value)
	delay(2000)
	stateFlow.collect { println("Received $it") }
	println()
}

/**
 * We typically use stateIn when we want to observe a value from one source of changes. On the way, these changes can
 * be processed, and in the end they can be observed by our views.
 */
//class LocationsViewModel(
//	locationService: LocationService
//) : ViewModel() {
//	private val location = locationService.observeLocations().map { it.toLocationsDisplay() }
//		.stateIn(
//			scope = viewModelScope,
//			started = SharingStarted.Lazily,
//			initialValue = LocationsDisplay.Loading,
//		)
//// ...
//}