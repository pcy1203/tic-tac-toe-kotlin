package com.example.tictactoe

sealed class ListItem {

    object ButtonItem : ListItem()
    data class HistoryBoardItem(val board: List<String>) : ListItem()
    data class CurrentBoardItem(val board: List<String>) : ListItem()
}