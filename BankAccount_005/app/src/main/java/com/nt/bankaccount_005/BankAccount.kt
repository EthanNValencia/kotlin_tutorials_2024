package com.nt.bankaccount_005

class BankAccount (private var accountHolder: String, private var balance: Double) {

    private val transactionHistory = mutableListOf<String>()

    enum class Action {DEPOSIT, WITHDRAW}

    fun accountBalance(): Double {
        return balance
    }

    fun deposit(amount: Double) {
        val newBalance = balance + amount
        addTransactionEvent(Action.DEPOSIT, newBalance, balance, amount)
        balance = newBalance
    }

    fun withdraw(amount: Double) {
        if(amount <= balance) {
            val newBalance = balance - amount
            addTransactionEvent(Action.WITHDRAW, newBalance, balance, amount)
            balance = newBalance
        } else {
            println("You cannot over draw. You only have $$balance and attempted to withdraw $$amount.")
        }
    }

    fun displayTransactionHistory() {
        println("Transaction History(${transactionHistory.size}) for $accountHolder")
        for(transaction in transactionHistory) {
            println(transaction)
        }
    }

    private fun addTransactionEvent(action: Action, newBalance: Double, balance: Double, amount: Double) {
        when (action) {
            Action.DEPOSIT -> transactionHistory.add("Action: $action +$$amount | oldBalance : $$balance | newBalance: $$newBalance")
            Action.WITHDRAW -> transactionHistory.add("Action: $action -$$amount | oldBalance : $$balance | newBalance: $$newBalance")
        }
    }
}