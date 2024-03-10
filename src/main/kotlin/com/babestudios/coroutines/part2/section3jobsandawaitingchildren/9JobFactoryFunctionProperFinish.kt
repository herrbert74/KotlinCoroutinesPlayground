package com.babestudios.coroutines.part2.section3jobsandawaitingchildren

import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * A better approach would be to join all the current children of the job.
 *
 * Job() is a great example of a factory function. At first, you might think that you’re calling a constructor of Job,
 * but you might then realize that Job is an interface, and interfaces cannot have constructors.
 * The reality is that it is a fake constructor - a simple function that looks like a constructor.
 * Moreover, the actual type returned by this function is not a Job but its subinterface CompletableJob.
 */
suspend fun main(): Unit = coroutineScope { val job = Job()
	launch(job) { // the new job replaces one from parent
		delay(1000)
		println("Text 1")
	}
	launch(job) { // the new job replaces one from parent
		delay(2000)
		println("Text 2")
	}
	job.children.forEach { it.join() }
}
// (1 sec)
// Text 1
// (1 sec)
// Text 2
