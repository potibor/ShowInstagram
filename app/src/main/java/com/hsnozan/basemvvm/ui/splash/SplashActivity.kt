package com.hsnozan.basemvvm.ui.splash

import android.content.Intent
import android.os.Bundle
import com.hsnozan.basemvvm.R
import com.hsnozan.basemvvm.core.BaseActivity
import com.hsnozan.basemvvm.databinding.ActivitySplashBinding
import com.hsnozan.basemvvm.ui.home.HomeActivity
import com.hsnozan.basemvvm.ui.login.LoginActivity
import com.hsnozan.basemvvm.utils.launchActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivity<SplashViewModel, ActivitySplashBinding>() {

    private val splashViewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        observeToken()
    }

    private fun observeToken() {
        splashViewModel.progressLiveData.observeForever {
            when (it) {
                true -> openHomeActivity()
                false -> openLoginActivity()
            }
        }
    }

    private fun openHomeActivity() {
        launchActivity<HomeActivity> {}
    }

    private fun openLoginActivity() {
        launchActivity<LoginActivity> {}
    }

    private fun initialize() {
        binding.viewmodel = viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun initViewModelClass(): Class<SplashViewModel> {
        return splashViewModel.javaClass
    }
}
