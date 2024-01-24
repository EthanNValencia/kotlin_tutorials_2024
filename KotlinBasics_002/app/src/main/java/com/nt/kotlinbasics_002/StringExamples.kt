package com.nt.kotlinbasics_002

fun main() {
    var str: String = "abcd 123"
    for(char in str) {
        print(char)
    }
    str = " Ethan"
    println(str.lowercase())
    println("\n${str.trim()} Nephew");
}