package com.seosh.simpleweather.view.base

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import com.seosh.simplesearch.view.base.BaseOneBtnDialog
import com.seosh.simplesearch.view.base.BaseTwoBtnDialog
import com.seosh.simpleweather.R

open class BaseActivity : AppCompatActivity() {
    companion object {
        private const val CLOSE_TIMEOUT = 2000
    }

    private val baseOneBtnDialog : BaseOneBtnDialog by lazy {
        BaseOneBtnDialog(this@BaseActivity)
    }

    private val baseTwoBtnDialog : BaseTwoBtnDialog by lazy {
        BaseTwoBtnDialog(this@BaseActivity)
    }

    private var onBackPressedTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private val progressDialog : AppCompatDialog by lazy {
        AppCompatDialog(this@BaseActivity).apply {
            setContentView(R.layout.dialog_progress)
            window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
            setCancelable(false)
        }
    }

    fun showProgressDialog() {
        if(!progressDialog.isShowing) {
            progressDialog.show()
        }
    }

    fun dismissProgressDialog() {
        if(progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }

    fun showOneBtnDialog(msg: String, callback: BaseOneBtnDialog.BaseDialogCallback) {
        baseOneBtnDialog.showDialog(msg, callback)
    }

    fun showTwoBtnDialog(msg: String, callback: BaseTwoBtnDialog.BaseDialogCallback) {
        baseTwoBtnDialog.showDialog(msg, callback)
    }

    override fun onBackPressed() {
        when(System.currentTimeMillis() > onBackPressedTime + CLOSE_TIMEOUT) {
            true -> {
                onBackPressedTime = System.currentTimeMillis()
                Toast.makeText(this@BaseActivity, resources.getString(R.string.text_noti_exit), Toast.LENGTH_SHORT).show()
            }

            false -> {
                finish()
            }
        }
    }
}