package com.seosh.simplesearch.view.base

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.TextView
import com.seosh.simpleweather.R

class BaseTwoBtnDialog(context: Context) : Dialog(context) {
    private var textContent: TextView
    private var btnOk: TextView
    private var btnCancel: TextView

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dailog_two_btn)
        setCancelable(false)

        textContent = findViewById(R.id.textContent)
        btnOk = findViewById(R.id.btnOk)
        btnCancel = findViewById(R.id.btnCancel)
    }

    fun showDialog(msg: String, callback: BaseDialogCallback?) {
        textContent.text = msg

        btnCancel.setOnClickListener {
            dismiss()
        }

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