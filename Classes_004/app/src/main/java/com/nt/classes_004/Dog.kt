package com.nt.classes_004

class Dog(var name: String = "", private var color: String, var breed: String = "", var age: Int) {

    private val colorString: String = "$name is $color."

    fun bark() {
        println("$name \"Woof woof\"")
    }

    fun introduce() {
        println("Hello! I am $name! I am $color!")
    }

    init {
        println(colorString)
    }

    override fun toString(): String {
        return "Dog(name='$name', color='$color', breed='$breed', age='$age', colorString='$colorString')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Dog
        if (name != other.name) return false
        if (color != other.color) return false
        if (breed != other.breed) return false
        if (age != other.age) return false
        return colorString == other.colorString
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + color.hashCode()
        result = 31 * result + breed.hashCode()
        result = 31 * result + age
        result = 31 * result + colorString.hashCode()
        return result
    }

}