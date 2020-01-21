package com.hsnozan.basemvvm.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.hsnozan.basemvvm.R
import com.hsnozan.basemvvm.core.BaseActivity
import com.hsnozan.basemvvm.databinding.ActivityLoginBinding
import com.hsnozan.basemvvm.ui.home.HomeActivity
import com.hsnozan.basemvvm.utils.*
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class LoginActivity : BaseActivity<LoginViewModel, ActivityLoginBinding>() {

    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        observeProgress()
        displayUrlOnWebView()
    }

    private fun initialize() {
        binding.viewmodel = viewModel
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun displayUrlOnWebView() {
        webView?.let {
            it.visibility = View.VISIBLE
            it.settings.javaScriptEnabled = true
            it.settings.loadWithOverviewMode = true
            it.settings.builtInZoomControls = true
            it.settings.displayZoomControls = false
            it.settings.useWideViewPort = true
            it.requestFocus()
            it.isScrollbarFadingEnabled = false
            it.isHorizontalScrollBarEnabled = false
            it.loadUrl(Constants.REQUEST_URL)
            it.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView, url: String) {
                    super.onPageFinished(view, url)

                    if (url.contains("?code=")) {
                        val uri = Uri.parse(url)
                        val decodedUri = uri.encodedQuery
                        loginViewModel.accessCode =
                            decodedUri?.substring(decodedUri.lastIndexOf("=") + 1)
                                ?: throw NullPointerException("Expression 'decodedUri' must not be null")
                        loginViewModel.getAccessToken()

                    }
                }
            }
        }
    }

    private fun observeProgress() {
        loginViewModel.progressLiveData.observeForever {
            hideKeyboardFrom()
            tryCatch(tryBlock = {
                launchActivity<HomeActivity> {  }
            })
        }
    }

    override fun initViewModelClass(): Class<LoginViewModel> {
        return loginViewModel.javaClass
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

}
