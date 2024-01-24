package com.nt.classes_004
// Classes arrays and loops.
fun main() {
    // dogExample()
    // coffeeExample()
    val shoppingListReadonly = listOf("CPU", "GPU", "RAM", "SSD") // this is immutable/readonly.
    val shoppingList = mutableListOf<String>("CPU", "GPU", "RAM", "SSD")
    shoppingList.add("PSU")
    println(shoppingList)
    shoppingList.add("error")
    println(shoppingList)
    shoppingList.remove("error") // that was easy
    println(shoppingList)

    val mixedList: List<Any> = mutableListOf(1, "Hello", 3.14, true)
    println(mixedList)
    // filterExampleOne()
    filterExampleTwo()
    containsExampleOne()
    containsAnyExample()
    forLoopExampleOne()
    forLoopExampleTwo()
    forLoopExampleThree()
}

fun forLoopExampleOne() {
    val shoppingList = mutableListOf<String>("CPU", "GPU", "RAM", "SSD")
    for(item in shoppingList) {
        print("| $item |")
        if(item == "RAM") {
            break // or break?
        }
    }
    println(" You broke out of the loop!")
}

fun forLoopExampleTwo() {
    val shoppingList = mutableListOf<String>("CPU", "GPU", "RAM", "SSD")
    for(index in 0 until shoppingList.size) { // This won't throw an out of bounds exception.
        print("| $index : ${shoppingList[index]} |")
    }
}

fun forLoopExampleThree() {
    val shoppingList = mutableListOf<String>("CPU", "GPU", "RAM", "SSD")
    for(index in 0..<shoppingList.size) { // This won't throw an out of bounds exception.
        print("| $index : ${shoppingList[index]} |")
    }
}

fun filterExampleOne() {
    val shoppingList = mutableListOf("CPU", "GPU", "RAM", "SSD")
    val filteredList = shoppingList.filter { !it.contains("PU") }
    println(filteredList)
}

fun filterExampleTwo() {
    val daisy = Dog("Daisy", "Black", "Pit", 12)
    val daisyTwo = Dog("Daisy", "Black", "Pit", 12)
    val ted = Dog("Ted", "White", "Bulldog", 10)
    val shoppingList = mutableListOf(daisy, daisyTwo, ted)
    val filteredList = shoppingList.filter { !it.name.contains("Daisy") }
    println(filteredList)
}

fun containsAnyExample() {
    val daisy = Dog("Daisy", "Black", "Pit", 12)
    val daisyTwo = Dog("Daisy", "Black", "Pit", 12)
    val ted = Dog("Ted", "White", "Bulldog", 10)
    val shoppingList = mutableListOf(daisy, daisyTwo, ted)

    // Check if the shoppingList contains a Dog with the breed "Bulldog"
    val containsBulldog = shoppingList.any { it.breed == "Bulldog" }

    println("Contains Bulldog: $containsBulldog")
}

fun containsExampleOne() {
    val daisy = Dog("Daisy", "Black", "Pit", 12)
    val daisyTwo = Dog("Daisy", "Black", "Pit", 12)
    val ted = Dog("Ted", "White", "Bulldog", 10)
    val shoppingList = mutableListOf(daisy, daisyTwo, ted)
    println(shoppingList.contains(ted))
}

fun coffeeExample() {
    val ethanCoffee = CoffeeDetails(3, "Ethan", Size.MEDIUM, 4)
    printCoffee(ethanCoffee)
}

enum class Size {SMALL, MEDIUM, LARGE}

data class CoffeeDetails(val sugarCount: Int, val customerName:String, val size: Size, val creamAmount: Int)

fun printCoffee(coffeeDetails: CoffeeDetails) {
    when {
        coffeeDetails.sugarCount == 1 -> println("One sugar is needed for ${coffeeDetails.customerName}.")
        coffeeDetails.sugarCount > 1 -> println("Multiple sugars are needed for ${coffeeDetails.customerName}.")
        else -> println("No sugar is needed for ${coffeeDetails.customerName}.")
    }
}

fun dogExample() {
    val daisy = Dog("Daisy", "Black", "Pit", 12)
    val daisyTwo = Dog("Daisy", "Black", "Pit", 12)
    val ted = Dog("Ted", "White", "Bulldog", 10)
    println(daisy == ted)
    println(daisy == daisyTwo)
    daisyTwo.age = 9
    println(daisy == daisyTwo)
}