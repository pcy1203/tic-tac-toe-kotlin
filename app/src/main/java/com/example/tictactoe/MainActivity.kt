package com.example.tictactoe

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var textViews: List<TextView>
    private lateinit var adapter: HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        textViews = listOf(
            binding.view1, binding.view2, binding.view3,
            binding.view4, binding.view5, binding.view6,
            binding.view7, binding.view8, binding.view9
        )

        viewModel.board.observe(this, Observer { board ->
            board.forEachIndexed { index, value ->
                textViews[index].text = value
            }
            val currentBoard: List<String> = board?: List(9) { "" }

            if(viewModel.winnerText.value != ""){
                adapter.addLast(currentBoard, viewModel.winnerText.value!!)
            }
            else{
                adapter.addHistory(currentBoard)
            }
        })

        viewModel.description.observe(this, Observer { description ->
            binding.description.text = description
        })

        textViews.forEachIndexed { index, textView ->
            textView.setOnClickListener {
                viewModel.boxClicked(index)
            }
        }
        binding.ResetButton.setOnClickListener{
            viewModel.resetButtonClicked()
            adapter.resetHistory()
        }
        binding.MenuIcon.setOnClickListener{
            binding.root.openDrawer(binding.drawer)
        }

        fun historyCallback(historyBoard:List<String>, position: Int){
            viewModel.historyButtonClicked(historyBoard)
            adapter.moveHistory(position)
        }

        binding.HistoryView.layoutManager = LinearLayoutManager(this)
        adapter = HistoryAdapter(
            listOf(
                ListItem.ButtonItem,
                ),
            ::historyCallback
        )
        binding.HistoryView.adapter = adapter
        /*
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        */
    }
}