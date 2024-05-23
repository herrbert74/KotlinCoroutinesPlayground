package com.babestudios.coroutines.part3.section3hotvscold

/**
 * Hot data streams are eager, produce elements independently of their consumption, and store the elements. Cold data
 * streams are lazy, perform their operations on-demand, and store nothing.
 * We can observe these differences when we use lists (hot) and sequences (cold). Builders and operations on hot data
 * streams start immediately. On cold data streams, they are not started until the elements are needed.
 */
fun main() {
	val l = buildList {
		repeat(3) {
			add("User$it")
			println("L: Added User")
		}
	}
	val l2 = l.map {
		println("L: Processing")
		"Processed $it"
	}
	val s = sequence {
		repeat(3) {
			yield("User$it")
			println("S: Added User")
		}
	}
	val s2 = s.map {
		println("S: Processing")
		"Processed $it"
	}
}