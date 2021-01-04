package com.seosh.simplesearch.view.base

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.TextView
import com.seosh.simpleweather.R

class BaseOneBtnDialog(context: Context) : Dialog(context) {
    private var textContent: TextView
    private var btnOk: TextView

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dailog_one_btn)
        setCancelable(false)

        textContent = findViewById(R.id.textContent)
        btnOk = findViewById(R.id.btnOk)
    }

    fun showDialog(msg: String, callback: BaseDialogCallback?) {
        textContent.text = msg

        btnOk.setOnClickListener {
            callback?.onClick()
            dismiss()
        }

        if(!this.isShowing) {
            show()
        }
    }

    interface BaseDialogCallback {
        fun onClick()
    }
}