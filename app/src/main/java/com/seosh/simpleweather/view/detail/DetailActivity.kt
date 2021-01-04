package com.seosh.simpleweather.view.detail

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.seosh.simplesearch.view.base.BaseOneBtnDialog
import com.seosh.simpleweather.R
import com.seosh.simpleweather.api.data.Status
import com.seosh.simpleweather.databinding.ActivityDetailBinding
import com.seosh.simpleweather.repository.data.CityItem
import com.seosh.simpleweather.utils.Constants
import com.seosh.simpleweather.utils.NetworkUtils
import com.seosh.simpleweather.view.base.BaseActivity
import com.seosh.simpleweather.viewmodel.DetailViewModel
import com.seosh.simpleweather.viewmodel.DetailViewModelFactory
import kotlinx.android.synthetic.main.layout_top_bar.view.*

class DetailActivity : BaseActivity(), OnMapReadyCallback {
    companion object {
        private const val TAG = "[DetailActivity]"
    }

    private val detailViewModel : DetailViewModel by lazy {
        ViewModelProvider(this@DetailActivity, DetailViewModelFactory()).get(DetailViewModel::class.java)
    }

    private var cityItem: CityItem? = null
    private var googleMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding : ActivityDetailBinding = DataBindingUtil.setContentView(this@DetailActivity, R.layout.activity_detail)

        with(binding) {
            lifecycleOwner = this@DetailActivity
            viewModel = detailViewModel

            setLayout(this)
            subscribeUI(this)
        }

        if(!NetworkUtils.isNetworkConnected(this@DetailActivity)) {
            showOneBtnDialog(getString(R.string.text_network_unstable), object: BaseOneBtnDialog.BaseDialogCallback {
                override fun onClick() {
                    finish()
                }
            })
            return
        }

        when(intent.hasExtra(Constants.EXTRA_CITY_ITEM)) {
            true -> {
                cityItem = intent.getSerializableExtra(Constants.EXTRA_CITY_ITEM) as? CityItem
                Log.d("seosh", "${TAG} [onCreate] Passed item from MainActivity : ${cityItem}")

                binding.layoutTopBar.textTitle.text = cityItem!!.name
                binding.cityItem = cityItem
                requestWeather(cityItem!!.name)
            }
        }
    }

    private fun requestWeather(city: String) {
        showProgressDialog()
        detailViewModel.requestWeather(city)
    }

    private fun setLayout(binding: ActivityDetailBinding) {
        binding.layoutTopBar.imageBack.setOnClickListener {
            onBackPressed()
        }

        val mapFragment = supportFragmentManager.findFragmentById(R.id.googleMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun subscribeUI(binding: ActivityDetailBinding) {
        detailViewModel.resultWeather.observe(this@DetailActivity, Observer { result ->
            dismissProgressDialog()

            when(result.status) {
                Status.SUCCESS -> {

                    if(result.data != null) {
                        Log.d("seosh", "${TAG} [subscribeUI] [resultWeather observe] data : ${result.data}")

                        // Set GoogleMap
                        if(googleMap != null && cityItem != null) {
                            setGoogleMap(cityItem!!)
                        }

                        // Set Detail Data
                        binding.weather = result.data.weather[0]
                        binding.climate = result.data.climate
                    }
                }

                Status.FAIL, Status.ERROR -> {
                    showOneBtnDialog(getString(R.string.text_network_unstable), object: BaseOneBtnDialog.BaseDialogCallback {
                        override fun onClick() {
                            finish()
                        }
                    })
                }
            }
        })
    }

    private fun setGoogleMap(item: CityItem) {
        val position = LatLng(item.coord.lat, item.coord.lon)

        val options = MarkerOptions().apply {
            position(position)
            title(item.name)
        }

        googleMap?.addMarker(options)
        googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 13f))
    }

    override fun onMapReady(map: GoogleMap?) {
        googleMap = map
    }

    override fun onBackPressed() {
        finish()
    }
}