package com.example.tictactoe

sealed class ListItem {

    object ButtonItem : ListItem()
    data class HistoryBoardItem(val board: List<String>) : ListItem()
    data class LastBoardItem(val board: List<String>, val winnerText: String) : ListItem()
}