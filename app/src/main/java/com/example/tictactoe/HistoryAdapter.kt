package com.example.tictactoe

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.tictactoe.databinding.HistoryBinding
import com.example.tictactoe.databinding.LastBinding

class HistoryAdapter (private var item: List<ListItem>, private val historyCallback: (List<String>, Int) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder> (){
    companion object {
        private const val TYPE_BUTTON = 0
        private const val TYPE_HISTORY = 1
        private const val TYPE_CURRENT = 2
    }
    override fun getItemViewType(position: Int): Int {
        return when (item[position]) {
            is ListItem.HistoryBoardItem -> TYPE_HISTORY
            is ListItem.LastBoardItem -> TYPE_CURRENT
            is ListItem.ButtonItem -> TYPE_BUTTON
            else -> throw IllegalArgumentException("Invalid View Type!!")
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType){
            TYPE_BUTTON -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.button, parent, false)
                ButtonViewHolder(view)
            }
            TYPE_HISTORY -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.history, parent, false)
                HistoryBoardViewHolder(view)
            }
            TYPE_CURRENT -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.last, parent, false)
                LastBoardViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid View Type!!")
        }
    }



    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ButtonViewHolder -> holder.bind()
            is LastBoardViewHolder -> holder.bind((item[position] as ListItem.LastBoardItem).board, (item[position] as ListItem.LastBoardItem).winnerText)
            is HistoryBoardViewHolder -> holder.bind((item[position] as ListItem.HistoryBoardItem).board, position, historyCallback)
        }
    }

    class ButtonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val button: TextView= itemView.findViewById(R.id.button)
        fun bind() {
            button.setOnClickListener {
                // Do something when button is clicked
            }
        }
    }
    class HistoryBoardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = HistoryBinding.bind(itemView)

        fun bind(board: List<String>, position: Int, historyCallback: (List<String>, Int) -> Unit) {
            val textViews = listOf(
                binding.view1, binding.view2, binding.view3,
                binding.view4, binding.view5, binding.view6,
                binding.view7, binding.view8, binding.view9
            )
            textViews.forEachIndexed { index, value ->
                textViews[index].text = board[index]
            }
            //ButtonViewHolder가 0번쨰 자리이기 때문에
            //position을 그대로 턴으로 사용
            binding.turnText.text = "${position}턴"
            binding.returnButton.setOnClickListener {
                historyCallback(board, position)
            }
        }
    }
    class LastBoardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = LastBinding.bind(itemView)
        fun bind(board: List<String>, winnerText: String) {
            val textViews = listOf(
                binding.view1, binding.view2, binding.view3,
                binding.view4, binding.view5, binding.view6,
                binding.view7, binding.view8, binding.view9
            )
            textViews.forEachIndexed { index, value ->
                textViews[index].text = board[index]
            }
            binding.winnerText.text = winnerText
        }
    }
    fun moveHistory(position: Int){
        val newItem: MutableList<ListItem> = item.toMutableList()
        Log.d("item", newItem.toString())
        Log.d("sliced", newItem.slice(0..<position).toString())
        item = newItem.slice(0..position)
        notifyDataSetChanged()
    }
    fun addHistory(newBoard: List<String>) {
        if(newBoard.all { it == "" }) return
        var newItem:MutableList<ListItem> = item.toMutableList()
        newItem.add(ListItem.HistoryBoardItem(newBoard))
        item = newItem
        notifyDataSetChanged()
    }
    fun addLast(lastBoard: List<String>, winnerText: String){
        var newItem: MutableList<ListItem> = item.toMutableList()
        newItem.add(ListItem.LastBoardItem(lastBoard, winnerText))
        item = newItem
        notifyDataSetChanged()
    }
    fun resetHistory() {
        var newItem:MutableList<ListItem> = mutableListOf(ListItem.ButtonItem)
        item = newItem
        notifyDataSetChanged()
    }
}