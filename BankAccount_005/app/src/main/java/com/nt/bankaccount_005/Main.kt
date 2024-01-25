package com.nt.bankaccount_005

fun main() {
    var bankAccount = BankAccount("Ethan", 40000.0)
    bankAccount.deposit(402.0)
    bankAccount.deposit(24.0)
    bankAccount.withdraw(2200.0)
    bankAccount.deposit(402.0)
    bankAccount.deposit(34.0)
    bankAccount.withdraw(105.0)
    bankAccount.deposit(402.0)
    bankAccount.deposit(200.0)
    bankAccount.displayTransactionHistory()
}