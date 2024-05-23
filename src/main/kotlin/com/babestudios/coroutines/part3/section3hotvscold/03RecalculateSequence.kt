package com.babestudios.coroutines.part3.section3hotvscold

/**
 * This means that a list is a collection of elements, but a sequence is just a definition of how these elements should
 * be calculated. Hot data streams:
 * • are always ready to be used(each operation can be a terminal operation);
 * • do not need to recalculate the result when used multiple times.
 */
fun m2(i: Int): Int {
	print("m$i ")
	return i * i
}

fun main() {
	val l = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
		.map { m2(it) } // m1 m2 m3 m4 m5 m6 m7 m8 m9 m10

	println(l) // [1, 4, 9, 16, 25, 36, 49, 64, 81, 100]
	println(l.find { it > 10 }) // 16
	println(l.find { it > 10 }) // 16
	println(l.find { it > 10 }) // 16

	val s = sequenceOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).map { m2(it) }

	println(s.toList())
	// [1, 4, 9, 16, 25, 36, 49, 64, 81, 100]
	println(s.find { it > 10 }) // m1 m2 m3 m4 16
	println(s.find { it > 10 }) // m1 m2 m3 m4 16
	println(s.find { it > 10 }) // m1 m2 m3 m4 16
}