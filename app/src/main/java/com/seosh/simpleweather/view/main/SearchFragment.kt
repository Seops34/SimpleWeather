package com.seosh.simpleweather.view.main

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.seosh.simpleweather.databinding.FragmentSearchBinding
import com.seosh.simpleweather.repository.data.CityItem
import com.seosh.simpleweather.utils.Constants
import com.seosh.simpleweather.view.adapter.CityAdapter
import com.seosh.simpleweather.view.base.BaseFragment
import com.seosh.simpleweather.view.detail.DetailActivity
import com.seosh.simpleweather.viewmodel.CityAdapterViewModel
import com.seosh.simpleweather.viewmodel.CityAdapterViewModelFactory
import com.seosh.simpleweather.viewmodel.SearchViewModel
import com.seosh.simpleweather.viewmodel.SearchViewModelFactory

class SearchFragment : BaseFragment() {
    companion object {
        private const val TAG = "[SearchFragment]"
    }

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var cityAdapter: CityAdapter

    private val searchViewModel : SearchViewModel by lazy {
        ViewModelProvider(this@SearchFragment, SearchViewModelFactory(requireContext())).get(SearchViewModel::class.java)
    }

    private val cityAdapterViewModel : CityAdapterViewModel by lazy {
        ViewModelProvider(this@SearchFragment, CityAdapterViewModelFactory()).get(CityAdapterViewModel::class.java)
    }

    private var cityList : List<CityItem>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSearchBinding.inflate(inflater, container, false)

        with(binding) {
            lifecycleOwner = this@SearchFragment
            viewModel = searchViewModel

            setLayout(this)
            subscribeUI(this)
        }

        requestCityList()

        return binding.root
    }

    private fun setLayout(binding: FragmentSearchBinding) {
        binding.textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun afterTextChanged(s: Editable?) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Log.d("seosh", "$TAG [TextWatcher] text : ${s.toString()}")
                cityAdapter.filter.filter(s.toString())
            }
        }

        linearLayoutManager = LinearLayoutManager(requireContext())
        cityAdapter = CityAdapter(cityAdapterViewModel)

        binding.recyclerViewCityList.apply {
            layoutManager = linearLayoutManager
            adapter = cityAdapter
        }
    }

    private fun requestCityList() {
        showProgressDialog()
        searchViewModel.requestCityList()
    }

    private fun subscribeUI(binding: FragmentSearchBinding) {
        searchViewModel.resultCityList.observe(viewLifecycleOwner, Observer { list ->
            cityList = list
            searchViewModel.getFavorites()
        })

        searchViewModel.resultFavorites.observe(viewLifecycleOwner, Observer { list ->
            val favoriteNameList = list.map {
                it.name
            }

            cityList?.forEach {
                if(favoriteNameList.contains(it.name)) {
                    it.isFavorite = !it.isFavorite
                }
            }

            dismissProgressDialog()

            cityAdapter.submitList(cityList)
        })

        cityAdapterViewModel.onSelectedItem.observe(viewLifecycleOwner, Observer { item ->
            startActivity(Intent(requireActivity(), DetailActivity::class.java).apply {
                putExtra(Constants.EXTRA_CITY_ITEM, item)
            })
        })

        cityAdapterViewModel.onSavedItem.observe(viewLifecycleOwner, Observer { item ->
            item.isFavorite = !item.isFavorite

            when(item.isFavorite) {
                true -> searchViewModel.insertFavorite(item)
                false -> searchViewModel.deleteFavorite(item)
            }
        })
    }
}