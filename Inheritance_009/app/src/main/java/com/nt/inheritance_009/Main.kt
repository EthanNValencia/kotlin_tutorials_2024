package com.nt.inheritance_009

fun main() {
    val obj1 = Base()
    obj1.baseFunction()

    val obj2 = Secondary()
    obj2.baseFunction()
    obj2.openBaseFunction()

    val obj3 = Tertiary()
    obj3.baseFunction()

    val obj4 = SecondaryChild()
    obj4.actionTwo()
    obj4.openBaseFunction()
}