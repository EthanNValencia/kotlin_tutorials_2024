package com.nt.rockpaperscissors_003

data class Options(val rock: String = "Rock", val paper: String = "Paper", val scissor: String = "Scissor")

fun main() {
    val options = Options()
    val computerChoice = computerPicks(options)
    val playerChoice = playerPicks(options)
    println(newWhoWon(computerChoice, playerChoice, options))
}

fun newWhoWon(computerChoice: String, playerChoice: String, options: Options): String {
    val computerWins = "Computer wins! $computerChoice beats $playerChoice"
    val playerWins = "Player wins! $playerChoice beats $computerChoice"
    val tie = "It was a tie! $computerChoice equals $playerChoice"
    val winner = when {
        computerChoice == playerChoice -> tie
        computerChoice == options.rock && playerChoice == options.paper -> computerWins
        computerChoice == options.paper && playerChoice == options.rock -> computerWins
        computerChoice == options.scissor && playerChoice == options.paper -> computerWins
        else -> playerWins
    }
    return winner
}

fun computerPicks(options: Options): String {
    when((1..3).random()) {
        1 -> { return options.rock }
        2 -> { return options.paper }
        3 -> { return options.scissor }
    }
    throw Exception("The computer failed to pick a valid choice!")
}

fun playerPicks(options: Options) : String {
    var validPick = false
    var playerInput = ""
    while (!validPick) {
        println("Enter your choice. ${options.rock}, ${options.paper}, or ${options.scissor}?")
        playerInput = readln()
        if(playerInput == options.rock || playerInput == options.paper || playerInput == options.scissor) {
            validPick = true
        }
    }
    return playerInput
}