package com.seosh.simpleweather.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.databinding.DataBindingUtil
import com.seosh.simplesearch.view.base.BaseOneBtnDialog
import com.seosh.simpleweather.R
import com.seosh.simpleweather.databinding.ActivitySplashBinding
import com.seosh.simpleweather.utils.NetworkUtils
import com.seosh.simpleweather.view.base.BaseActivity
import com.seosh.simpleweather.view.main.MainActivity

class SplashActivity : BaseActivity() {

    companion object {
        private const val SPLASH_DEALY = 1000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivitySplashBinding = DataBindingUtil.setContentView(this@SplashActivity, R.layout.activity_splash)

        initSettings()
    }

    private fun initSettings() {
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            moveSearchActivity()
        }, SPLASH_DEALY)
    }

    private fun moveSearchActivity() {
        when(NetworkUtils.isNetworkConnected(applicationContext)) {
            true -> {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }

            false -> {
                showOneBtnDialog(getString(R.string.text_network_unstable), object: BaseOneBtnDialog.BaseDialogCallback {
                    override fun onClick() {
                        finish()
                    }
                })
            }
        }
    }
}
