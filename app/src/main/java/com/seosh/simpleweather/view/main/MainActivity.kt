package com.seosh.simpleweather.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayoutMediator
import com.seosh.simpleweather.R
import com.seosh.simpleweather.databinding.ActivityMainBinding
import com.seosh.simpleweather.view.adapter.WeatherPagerAdapter
import com.seosh.simpleweather.view.base.BaseActivity
import kotlinx.android.synthetic.main.layout_top_bar.view.*

class MainActivity : BaseActivity() {

    private val tabTextList = arrayListOf("Search", "Favorites")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)

        with(binding) {
            lifecycleOwner = this@MainActivity

            setLayout(this)
        }
    }

    private fun setLayout(binding: ActivityMainBinding) {
        binding.layoutTopBar.imageBack.visibility = View.GONE

        binding.viewPagerMain.apply {
            adapter = WeatherPagerAdapter(this@MainActivity)
        }

        with(binding) {
                TabLayoutMediator(tabLayoutMain, viewPagerMain) { tab, position ->
                tab.text = tabTextList[position]
            }
        }.attach()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}