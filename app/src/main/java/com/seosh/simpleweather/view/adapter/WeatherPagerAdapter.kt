package com.seosh.simpleweather.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.seosh.simpleweather.view.main.FavoriteFragment
import com.seosh.simpleweather.view.main.SearchFragment

class WeatherPagerAdapter(private val fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    private var fragmentList = ArrayList<Fragment>()

    init {
        fragmentList.apply {
            add(SearchFragment())
            add(FavoriteFragment())
        }
    }

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}