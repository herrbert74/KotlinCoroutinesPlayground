package com.babestudios.coroutines.part2.section9problemwithsharedstate

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit


/**
 * If we mentioned Mutex, we should also mention Semaphore, which works in similar way, but can have more than one
 * permit. Regarding Mutex, we speak of a single lock, so it has functions lock, unlock and withLock.
 * Regarding Semaphore, we speak of permits, so it has functions acquire, release and withPermit.
 */
suspend fun main() = coroutineScope {
	val semaphore = Semaphore(2)

	repeat(5) {
		launch {
			semaphore.withPermit {
				delay(1000)
				print(it)
			}
		}
	}
}