package com.example.tms_android

import DynamicFragmentAdapter
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.tms_android.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

/*
* Задача 2: ViewPager2 с Tabs
Сделай экран с TabLayout и ViewPager2, в каждом табе — отдельный фрагмент
* */
class MainActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        setupViewPager()

        ViewCompat.setOnApplyWindowInsetsListener(viewBinding.root) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)

            val imeVisible = insets.isVisible(WindowInsetsCompat.Type.ime())

            if(imeVisible)
                WindowInsetsControllerCompat(window, window.decorView).hide(WindowInsetsCompat.Type.navigationBars())
            else
                WindowInsetsControllerCompat(window, window.decorView).show(WindowInsetsCompat.Type.navigationBars())

            insets
        }

        viewBinding.buttonAddFragment.setOnClickListener {
            (viewBinding.viewPager.adapter as DynamicFragmentAdapter).addFragment(
                FragmentOne()
            )
        }
    }

    private fun setupViewPager() {
        viewBinding.let {
            val adapter = DynamicFragmentAdapter(this)
            it.viewPager.adapter = adapter
            TabLayoutMediator(it.tabLayout, it.viewPager) { tab, position ->
                tab.text = "Tab ${position + 1}"
                tab.icon = getDrawable(R.drawable.baseline_123_24)
            }.attach()
        }
    }
}