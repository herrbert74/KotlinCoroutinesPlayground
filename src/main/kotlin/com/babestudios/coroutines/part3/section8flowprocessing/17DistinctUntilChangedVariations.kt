package com.babestudios.coroutines.part3.section8flowprocessing

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList

/**
 * There are also variants of this function. The first one, distinctUntilChangedBy, specifies a key selector to be
 * compared in order to check if two elements are equal. The second one, distinctUntilChanged with a lambda expression,
 * specifies how two elements should be compared (instead of equals, which is used by default).
 */
data class User(val id: Int, val name: String) {
	override fun toString(): String = "[$id] $name"
}

suspend fun main() {
	val users = flowOf(
		User(1, "Alex"),
		User(1, "Bob"),
		User(2, "Bob"),
		User(2, "Celine")
	)
	println(users.distinctUntilChangedBy { it.id }.toList())
	// [[1] Alex, [2] Bob]
	println(users.distinctUntilChangedBy { it.name }.toList())
	// [[1] Alex, [1] Bob, [2] Celine]
	println(users.distinctUntilChanged { prev, next ->
		prev.id == next.id || prev.name == next.name
	}.toList()) // [[1] Alex, [2] Bob]
	// [2] Bob was emitted,
	// because we compare to the previous emitted
}

