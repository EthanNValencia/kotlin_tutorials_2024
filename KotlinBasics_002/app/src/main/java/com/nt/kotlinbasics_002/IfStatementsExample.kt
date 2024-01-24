package com.nt.kotlinbasics_002

fun main() {
    // println("What is your age?")
    // var age = readln().toInt()
    // check(readln().toInt())
    val testNumberObj = TestNumber(1, 3)

    // println(testNumberObj.numOne)
    range(20)
}

fun range(number: Int) {
    if(number in 10..20) {
        println("$number is in the 10..20 range.")
    }
}

fun check(age: Int) {
    if(age >= 21) {
        println("Big enough")
    } else {
        check(fakeAge())
    }
}

fun fakeAge(): Int {
    return (Math.random() * 30).toInt();
}