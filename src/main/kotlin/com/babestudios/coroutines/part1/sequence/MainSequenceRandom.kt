package com.babestudios.coroutines.part1.sequence

import java.util.*

fun main() {
    print(randomNumbers().take(10).toList())
}

fun randomNumbers(
    seed: Long = System.currentTimeMillis()
): Sequence<Int> = sequence {
    val random = Random(seed)
    while (true) {
        yield(random.nextInt())
    }
}
