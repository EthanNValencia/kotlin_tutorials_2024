package com.nt.kotlinbasics_002

data class Test(var test1: Int, var test2: Int) {
    val add: Int
        get() = test1 + test2
    val subtract: Int
        get() = test1 - test2
}

data class U_Short(var uShort: UShort)

fun main() {
    val myAge = 34
    val test = Test(1,2)
    println("Hello world!")
    println(test.test1)
    println(myAge)
    val pi = 3.14
    println(pi)
    println(test.add)
    println(test.subtract)

    val short_example = U_Short(34u)
    println(short_example.uShort)
}

