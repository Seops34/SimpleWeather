package com.seosh.simpleweather.view.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.seosh.simpleweather.databinding.FragmentFavoriteBinding
import com.seosh.simpleweather.repository.data.CityItem
import com.seosh.simpleweather.repository.data.CityPosition
import com.seosh.simpleweather.utils.Constants
import com.seosh.simpleweather.view.adapter.CityAdapter
import com.seosh.simpleweather.view.base.BaseFragment
import com.seosh.simpleweather.view.detail.DetailActivity
import com.seosh.simpleweather.view.main.data.Status
import com.seosh.simpleweather.viewmodel.CityAdapterViewModel
import com.seosh.simpleweather.viewmodel.CityAdapterViewModelFactory
import com.seosh.simpleweather.viewmodel.FavoriteViewModel
import com.seosh.simpleweather.viewmodel.FavoriteViewModelFactory

class FavoriteFragment : BaseFragment() {
    companion object {
        private const val TAG = "[FavoriteFragment]"
    }

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var cityAdapter: CityAdapter

    private val cityAdapterViewModel : CityAdapterViewModel by lazy {
        ViewModelProvider(this@FavoriteFragment, CityAdapterViewModelFactory()).get(
            CityAdapterViewModel::class.java)
    }

    private val favoriteViewModel : FavoriteViewModel by lazy {
        ViewModelProvider(this@FavoriteFragment, FavoriteViewModelFactory(requireContext())).get(FavoriteViewModel::class.java)
    }

    private var cityList : ArrayList<CityItem>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        with(binding) {
            lifecycleOwner = this@FavoriteFragment
            viewModel = favoriteViewModel

            setLayout(this)
            subscribeUI(this)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        getFavorites()
    }

    private fun getFavorites() {
        showProgressDialog()
        favoriteViewModel.getFavorites()
    }

    private fun setLayout(binding: FragmentFavoriteBinding) {
        linearLayoutManager = LinearLayoutManager(requireContext())
        cityAdapter = CityAdapter(cityAdapterViewModel)

        binding.recyclerViewFavorites.apply {
            layoutManager = linearLayoutManager
            adapter = cityAdapter
        }
    }

    private fun subscribeUI(binding: FragmentFavoriteBinding) {
        favoriteViewModel.resultFavorites.observe(viewLifecycleOwner, Observer { list ->
            dismissProgressDialog()

            when(!list.isNullOrEmpty()) {
                true -> {
                    binding.dataStatus = Status.DATA

                    cityList = ArrayList<CityItem>()
                    list.forEach { item ->
                        cityList!!.add(CityItem(item.id, item.name, item.country, CityPosition(item.lat, item.lon), true, false))
                    }

                    // Log.d("seosh", "${TAG} [subscribeUI] City List in Databse\n${cityList.toString()}")

                    cityAdapter.submitList(cityList)
                }

                false -> {
                    binding.dataStatus = Status.NO_DATA
                }
            }
        })

        cityAdapterViewModel.onSelectedItem.observe(viewLifecycleOwner, Observer { item ->
            startActivity(Intent(requireActivity(), DetailActivity::class.java).apply {
                putExtra(Constants.EXTRA_CITY_ITEM, item)
            })
        })
    }
}