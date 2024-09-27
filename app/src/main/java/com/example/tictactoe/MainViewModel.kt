package com.example.tictactoe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _board = MutableLiveData<List<String>>(List(9) { "" })
    val board: LiveData<List<String>> = _board

    private val _description = MutableLiveData<String>("플레이어 1의 차례입니다.")
    val description: LiveData<String> = _description

    private var _end = MutableLiveData<Boolean>(false)

    private var _winnerText = MutableLiveData<String>("")
    val winnerText: LiveData<String> = _winnerText

    private var _isFirstPlayer: Boolean = true

    fun boxClicked(index: Int) {
        if (_end.value!!) return
        val currentBoard = _board.value ?: return
        if (currentBoard[index].isNotEmpty()) return
        val newBoard = currentBoard.toMutableList()

        if (_isFirstPlayer) {
            newBoard[index] = "O"
            _description.value = "플레이어 2의 차례입니다."
            if (checkWin(index, "O")) {
                _description.value = "플레이어 1의 승리입니다."
                _winnerText.value = "O의 승리!"
                _end.value = true
            }
            else if (newBoard.all { it == "O" || it == "X"}){
                _description.value = "무승부입니다."
                _end.value = true
            }
        } else {
            newBoard[index] = "X"
            _description.value = "플레이어 1의 차례입니다."
            if (checkWin(index, "X")) {
                _description.value = "플레이어 2의 승리입니다."
                _winnerText.value = "X의 승리!"
                _end.value = true
            }
            else if (newBoard.all { it == "O" || it == "X"}){
                _description.value = "무승부입니다."
                _end.value = true
            }
        }
        _board.value = newBoard
        _isFirstPlayer = !_isFirstPlayer
    }

    fun historyButtonClicked(returnBoard: List<String>): Unit{
        _board.value = returnBoard
        val numberOf1 = returnBoard.count({it -> it == "O"})
        val numberOf2 = returnBoard.count({it -> it == "X"})
        _isFirstPlayer = if(numberOf1 == numberOf2) true else false
        _description.value = if(_isFirstPlayer) "플레이어 1의 차례입니다." else "플레이어 2의 차례입니다."
        _end.value = false
        _winnerText.value = ""
    }

    fun resetButtonClicked(){
        _board.value = List(9) { "" }
        _description.value = "플레이어 1의 차례입니다."
        _end.value = false
        _isFirstPlayer = true
        _winnerText.value = ""
    }



    private fun checkWin(currentIndex: Int, currentString: String): Boolean {
        val rowMod = currentIndex / 3
        val colMod = currentIndex % 3

        if (_board.value?.get((rowMod * 3) + ((colMod + 1) % 3)) == currentString
            && _board.value?.get((rowMod * 3) + ((colMod + 2) % 3)) == currentString) {
            return true
        }

        if (_board.value?.get(((rowMod + 1) % 3) * 3 + colMod) == currentString
            && _board.value?.get(((rowMod + 2) % 3) * 3 + colMod) == currentString) {
            return true
        }

        if (currentIndex % 2 == 0
            && (((rowMod + 1) % 3) * 3 + ((colMod + 1) % 3)) % 2 == 0
            && (((rowMod + 2) % 3) * 3 + ((colMod + 2) % 3)) % 2 == 0
            && _board.value?.get(((rowMod + 1) % 3) * 3 + ((colMod + 1) % 3)) == currentString
            && _board.value?.get(((rowMod + 2) % 3) * 3 + ((colMod + 2) % 3)) == currentString) {
            return true
        }

        if (currentIndex % 2 == 0
            && (((rowMod + 2) % 3) * 3 + ((colMod + 1) % 3)) % 2 == 0
            && (((rowMod + 1) % 3) * 3 + ((colMod + 2) % 3)) % 2 == 0
            && _board.value?.get(((rowMod + 2) % 3) * 3 + ((colMod + 1) % 3)) == currentString
            && _board.value?.get(((rowMod + 1) % 3) * 3 + ((colMod + 2) % 3)) == currentString) {
            return true
        }
        return false
    }


}