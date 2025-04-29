package com.example.tms_android

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.tms_android.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: PostsAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        swipeRefreshLayout = binding.swiperefresh
        recyclerView = binding.recyclerView

        setupRecyclerView()
        setupSwipeRefresh()
        loadPosts()
    }

    private fun setupRecyclerView() {
        adapter = PostsAdapter { post ->
            Toast.makeText(this, "Button clicked in post ${post.id}", Toast.LENGTH_SHORT).show()
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun setupSwipeRefresh() {
        swipeRefreshLayout.setOnRefreshListener {
            loadPosts()
        }
    }

    private fun loadPosts() {
        swipeRefreshLayout.isRefreshing = true

        adapter.updateItems(
            listOf(
                PostItem.AuthorPost(1, "@sasha", "This is a text post from @sasha #${(10..100).random()}"),
                PostItem.ImagePost(2, "https://cataas.com/cat", "Hey, nice cat #${(10..100).random()}"),
                PostItem.TextWithButtonPost(3, "Please click the button below", "Click me plz"),
                PostItem.AuthorPost(4, "@anton", "Да норм у меня имя!"),
                PostItem.ImagePost(5, "https://cataas.com/cat", "Nice cat #${(10..100).random()}"),
                PostItem.TextWithButtonPost(6, "Подпишись и жми на колокольчик!", "Колокольчик"),
                PostItem.AuthorPost(7, "@liu_qiang", "大家好，你们今天怎么样？")
            )
        )

        swipeRefreshLayout.isRefreshing = false
    }
}