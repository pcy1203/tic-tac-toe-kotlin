package com.example.tictactoe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.tictactoe.databinding.CurrentBinding
import com.example.tictactoe.databinding.HistoryBinding

class HistoryAdapter (private var item: List<ListItem>): RecyclerView.Adapter<RecyclerView.ViewHolder> (){
    companion object {
        private const val TYPE_BUTTON = 0
        private const val TYPE_HISTORY = 1
        private const val TYPE_CURRENT = 2
    }

    override fun getItemViewType(position: Int): Int {
        return when (item[position]) {
            is ListItem.HistoryBoardItem -> TYPE_HISTORY
            is ListItem.CurrentBoardItem -> TYPE_CURRENT
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
                val view = LayoutInflater.from(parent.context).inflate(R.layout.current, parent, false)
                CurrentBoardViewHolder(view)
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
            is CurrentBoardViewHolder -> holder.bind((item[position] as ListItem.CurrentBoardItem).board)
            is HistoryBoardViewHolder -> holder.bind((item[position] as ListItem.HistoryBoardItem).board)
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

        fun bind(board: List<String>) {
            val textViews = listOf(
                binding.view1, binding.view2, binding.view3,
                binding.view4, binding.view5, binding.view6,
                binding.view7, binding.view8, binding.view9
            )
            textViews.forEachIndexed { index, value ->
                textViews[index].text = board[index]
            }
        }
    }
    class CurrentBoardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = CurrentBinding.bind(itemView)
        fun bind(board: List<String>) {
            val textViews = listOf(
                binding.view1, binding.view2, binding.view3,
                binding.view4, binding.view5, binding.view6,
                binding.view7, binding.view8, binding.view9
            )
            textViews.forEachIndexed { index, value ->
                textViews[index].text = board[index]
            }
        }
    }
    fun addHistory(newBoard: List<String>) {
        var newItem:MutableList<ListItem> = item.toMutableList()
        newItem += ListItem.HistoryBoardItem(newBoard)
        item = newItem
        notifyDataSetChanged()
    }
    fun resetHistory() {
        var newItem:MutableList<ListItem> = mutableListOf(ListItem.ButtonItem)
        item = newItem
        notifyDataSetChanged()
    }
}