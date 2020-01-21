package com.hsnozan.basemvvm.core

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders

abstract class BaseFragment<VModel : ViewModel, DataBinding: ViewDataBinding> : Fragment(){

    lateinit var binding : DataBinding
    lateinit var viewModel: VModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = createViewModel()
    }

    private fun createViewModel(): VModel {
        viewModel = ViewModelProviders.of(this).get(initViewModelClass())
        return viewModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,getLayoutID(),container,false)
        return binding.root
    }

    abstract fun getLayoutID(): Int

    abstract fun initViewModelClass(): Class<VModel>

}
