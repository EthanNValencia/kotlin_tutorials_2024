package com.nt.kotlinbasics_002

fun main() {
    makeCoffee(1, "Tim")
    makeCoffee(2, "Bob")
    makeCoffee(30, "Tob")
    makeCoffee(0, "Bim")
    makeCoffee(12)
}

fun makeCoffee(sugarCount : Int, name: String = "Anonymous") {
    when {
        sugarCount == 1 -> println("Coffee with $sugarCount spoon of sugar for $name.")
        sugarCount > 1 -> println("Coffee with $sugarCount spoons of sugar for $name.")
        else -> println("Coffee with no sugar for $name.")
    }
}