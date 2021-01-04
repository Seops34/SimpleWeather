package com.seosh.simpleweather.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

object NetworkUtils {

    fun isNetworkConnected(context: Context) : Boolean {
        val cm: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        when(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            true -> {
                val network = cm.activeNetwork
                val connection = cm.getNetworkCapabilities(network)

                return connection != null && (connection.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                                connection.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
            }

            false -> {
                val activeNetwork = cm.activeNetworkInfo

                if(activeNetwork != null) {
                    return (activeNetwork.type == ConnectivityManager.TYPE_WIFI ||
                            activeNetwork.type == ConnectivityManager.TYPE_MOBILE)
                }

                return false
            }
        }
    }
}