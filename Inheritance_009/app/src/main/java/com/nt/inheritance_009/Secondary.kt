package com.nt.inheritance_009


open class Secondary: Base() {

    override fun openBaseFunction() {
        // super.openBaseFunction()
        println("In SecondaryClass.openBaseFunction()")
    }

}
