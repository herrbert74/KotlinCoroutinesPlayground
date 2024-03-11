package com.babestudios.coroutines.part2.section6scopefunctions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

/**
 * In the below code, we would like to at least see Tweets, even if we have a problem fetching user details.
 * Unfortunately, an exception on getFollowersNumber breaks async, which breaks the whole scope and ends the program.
 * Instead, we would prefer a function that just throws an exception if it occurs.
 * Time to introduce our hero: coroutineScope (in the next section).
 */
data class Details(val name: String, val followers: Int)

data class Tweet(val text: String)

fun getFollowersNumber(): Int = throw Error("Service exception")

suspend fun getUserName(): String {
	delay(500)
	return "marcinmoskala"
}

suspend fun getTweets(): List<Tweet> {
	return listOf(Tweet("Hello, world"))
}

suspend fun CoroutineScope.getUserDetails(): Details {
	val userName = async { getUserName() }
	val followersNumber = async { getFollowersNumber() }
	return Details(userName.await(), followersNumber.await())
}

fun main() = runBlocking {
	val details = try {
		getUserDetails()
	} catch (e: Error) {
		null
	}
	val tweets = async { getTweets() }
	println("User: $details")
	println("Tweets: ${tweets.await()}")
}
// Only Exception...