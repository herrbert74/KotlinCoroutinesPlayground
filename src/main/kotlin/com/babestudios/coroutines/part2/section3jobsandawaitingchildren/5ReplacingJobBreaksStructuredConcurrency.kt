package com.babestudios.coroutines.part2.section3jobsandawaitingchildren

import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * The parent does not wait for its children because it has no relation with them.
 * This is because the child uses the job from the argument as a parent, so it has no relation to the runBlocking.
 *
 * When a coroutine has its own (independent) job, it has nearly no relation to its parent.
 * It only inherits other contexts, but other results of the parent-child relationship will not apply.
 * This causes us to lose structured concurrency, which is a problematic situation that should be avoided.
 */
fun main(): Unit = runBlocking {
	launch(Job()) { // the new job replaces one from parent
		delay(1000)
		println("Will not be printed")
	}
}
// (prints nothing, finishes immediately)
