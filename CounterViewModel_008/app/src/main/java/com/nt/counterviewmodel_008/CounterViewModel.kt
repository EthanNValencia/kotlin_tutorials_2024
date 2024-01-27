package com.nt.shoppinglistapp_007

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import com.nt.counterviewmodel_008.CounterRepository

class CounterViewModel(): ViewModel() {
    private val _counterRepository: CounterRepository = CounterRepository()
    private val _count = mutableIntStateOf(_counterRepository.getCounterModel().count)
    val count: MutableState<Int> = _count

    fun increment() {
        _counterRepository.incrementCounter()
        _count.value = _counterRepository.getCounterModel().count
    }

    fun decrement() {
        _counterRepository.decrementCounter()
        _count.value = _counterRepository.getCounterModel().count
    }
}