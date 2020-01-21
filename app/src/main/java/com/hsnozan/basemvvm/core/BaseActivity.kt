package com.hsnozan.basemvvm.core

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders

abstract class BaseActivity<VModel : ViewModel, DataBinding : ViewDataBinding> :
    AppCompatActivity() {

    lateinit var binding: DataBinding
    lateinit var viewModel: VModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = createViewModel()
        binding = DataBindingUtil.setContentView(this,getLayoutId())
    }

    abstract fun getLayoutId(): Int

    private fun createViewModel(): VModel {
        viewModel = ViewModelProviders.of(this).get(initViewModelClass())
        return viewModel
    }


    abstract fun initViewModelClass(): Class<VModel>

    fun showAlertDialog(context: Context, title: String,
                        positiveButton: String, positiveListener: DialogInterface.OnClickListener,
                        message: String, cancelable: Boolean) {

        val alertDialog = AlertDialog.Builder(context)
            .setPositiveButton(positiveButton, positiveListener).create()

        alertDialog.setTitle(title)
        alertDialog.setMessage(message)
        alertDialog.setCanceledOnTouchOutside(cancelable)
        alertDialog.setCancelable(cancelable)
        alertDialog.show()
    }
}