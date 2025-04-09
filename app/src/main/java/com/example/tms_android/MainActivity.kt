package com.example.tms_android

import DynamicFragmentAdapter
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.tms_android.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
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
            val createdIndex = (viewBinding.viewPager.adapter as DynamicFragmentAdapter).addFragment(
                FragmentOne()
            )

            val snackbar = Snackbar.make(viewBinding.root, getString(R.string.cancel), Snackbar.LENGTH_LONG)

            snackbar.setAction(getString(R.string.cancel)) {
                Toast.makeText(viewBinding.root.context, getString(R.string.new_fragment_cancel), Toast.LENGTH_SHORT).show()
                (viewBinding.viewPager.adapter as DynamicFragmentAdapter).removeFragment(createdIndex)
            }

            snackbar.show()
        }
    }

    private fun setupViewPager() {
        viewBinding.let {
            val adapter = DynamicFragmentAdapter(this)
            it.viewPager.adapter = adapter
            TabLayoutMediator(it.tabLayout, it.viewPager) { tab, position ->
                tab.text = "Tab ${position + 1}"
                tab.icon = AppCompatResources.getDrawable(this, R.drawable.baseline_123_24)
            }.attach()
        }
    }
}