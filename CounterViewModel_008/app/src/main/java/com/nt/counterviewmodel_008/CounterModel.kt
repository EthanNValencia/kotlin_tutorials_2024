package com.nt.counterviewmodel_008

data class CounterModel(var count: Int) {

}

class CounterRepository {

    private var counterModel = CounterModel(0)

    fun getCounterModel(): CounterModel {
        return counterModel
    }

    fun incrementCounter() {
        counterModel.count++
    }

    fun decrementCounter() {
        counterModel.count--
    }

}