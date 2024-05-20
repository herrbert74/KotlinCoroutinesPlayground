package com.babestudios.coroutines.part2.section9problemwithsharedstate

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * In practice, this approach can be used in two ways. The first approach is known as coarse-grained thread confinement.
 * This is an easy approach whereby we just wrap the whole function with withContext,
 * with a dispatcher limited to a single thread. This solution is easy and eliminates conflicts,
 * but the problem is that we lose the multithreading capabilities of the whole function.
 * Let’s take a look at the example below. api.fetchUser(id) could be started concurrently on many threads,
 * but its body will be running on a dispatcher that is limited to a single thread.
 * As a result, this function execution could slow down when we invoke functions that are blocking or CPU-intensive.
 */
class UserDownloader3(
	private val api: NetworkService
) {
	private val users = mutableListOf<User>()
	private val dispatcher = Dispatchers.IO
		.limitedParallelism(1)

	suspend fun downloaded(): List<User> =
		withContext(dispatcher) {
			users.toList()
		}

	suspend fun fetchUser(id: Int) = withContext(dispatcher) {
		val newUser = api.fetchUser(id)
		users += newUser
	}
}

suspend fun main() {
	val downloader = UserDownloader3(FakeNetworkService())
	coroutineScope {
		repeat(1_000_000) {
			launch {
				downloader.fetchUser(it)
			}
		}
	}
	print(downloader.downloaded().size) // ~1000000
}
