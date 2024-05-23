package com.babestudios.coroutines.part3.section3hotvscold

/**
 * Sequence processing does fewer operations because it processes elements lazily. The way it works is very simple.
 * Each intermediate operation (like map or filter) just decorates the previous sequence with a new operation.
 * The terminal operation does all the work. Think of the example below. In the case of a sequence,
 * find asks the result of the map for the first element. It asks the sequence returned from the sequenceOf (returns 1),
 * then maps it (to 1) and returns it to the filter. filter checks if this is an element that fulfills its criteria.
 * If the element does not fulfill the criteria, filter asks again and again until the proper element is found.
 * This is very different from list processing, which at every intermediate step calculates and returns a fully
 * processed collection. This is why the order of element processing is different and collection processing takes
 * more memory and might require more operations (like in the example below).
 *
 */
fun m(i: Int): Int {
	print("m$i ")
	return i * i
}

fun f(i: Int): Boolean {
	print("f$i ")
	return i >= 10
}

fun main() {
	listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
		.map { m(it) }
		.find { f(it) }
		.let { print(it) }
	// m1 m2 m3 m4 m5 m6 m7 m8 m9 m10 f1 f4 f9 f16 16
	println()
	sequenceOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
		.map { m(it) }
		.find { f(it) }
		.let { print(it) }
	// m1 f1 m2 f4 m3 f9 m4 f16 16
}
