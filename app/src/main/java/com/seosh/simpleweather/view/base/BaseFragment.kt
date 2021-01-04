package com.seosh.simpleweather.view.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatDialog
import androidx.fragment.app.Fragment
import com.seosh.simpleweather.R

open class BaseFragment : Fragment() {
    private val progressDialog : AppCompatDialog by lazy {
        AppCompatDialog(requireActivity()).apply {
            setContentView(R.layout.dialog_progress)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
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
}