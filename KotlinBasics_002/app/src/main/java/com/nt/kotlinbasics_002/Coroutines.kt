package com.nt.kotlinbasics_002

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() {
    println("Begin")
    runBlocking {
        // terribleExample()
        betterExample()
    }
    println("End")
}

suspend fun networkCall1(): String {
    // Simulating a network call
    kotlinx.coroutines.delay(1000)
    return "Result from networkCall1"
}

suspend fun networkCall2(): String {
    // Simulating another network call
    kotlinx.coroutines.delay(1500)
    return "Result from networkCall2"
}

fun betterExample() { // Async and await are probably best to use.
    runBlocking {
        val time = measureTimeMillis {
            val answer1 = async { networkCall1() }
            val answer2 = async { networkCall2() }
            println("answer1: ${answer1.await()}")
            println("answer2: ${answer2.await()}")
        }
        println("Time in Milliseconds: $time")
    }
}

fun terribleExample() {
    runBlocking {
        val time = measureTimeMillis {
            var answer1: String? = null
            var answer2: String? = null

            val job1 = launch { answer1 = networkCall1() }
            val job2 = launch { answer2 = networkCall2() }

            job1.join()
            job2.join()

            println("answer1: $answer1")
            println("answer2: $answer2")
        }
        println("Time in Milliseconds: $time")
    }
}