package com.babestudios.coroutines.part1.whycoroutines

import kotlin.concurrent.thread

fun main(args: Array<String>) {
    repeat(100_000) {
        thread {
            Thread.sleep(1000L)
            print(".")
        }
    }
}
