package com.babestudios.coroutines.part2.section9problemwithsharedstate

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex

/**
 * The last popular approach is to use a Mutex. You can imagine it as a room with a single key (or maybe a toilet at a
 * cafeteria). Its most important function is lock. When the first coroutine calls it, it kind of takes the key and
 * passes through lock without suspension. If another coroutine then calls lock, it will be suspended until the first
 * coroutine calls unlock (like a person waiting for a key to the toilet).
 * If another coroutine reaches the lock function, it is suspended and put in a queue, just after the second coroutine.
 * When the first coroutine finally calls the unlock function, it gives back the key, so the second coroutine
 * (the first one in the queue) is now resumed and can finally pass through the lock function.
 * Thus, only one coroutine will be between lock and unlock.
 */
suspend fun main() = coroutineScope {
	repeat(5) {
		launch {
			delayAndPrint()
		}
	}
}

private val mutex = Mutex()

suspend fun delayAndPrint() {
	mutex.lock()
	delay(1000)
	println("Done")
	mutex.unlock()
}