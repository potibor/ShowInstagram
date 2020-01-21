package com.hsnozan.basemvvm.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.hsnozan.basemvvm.BaseApplication
import com.hsnozan.basemvvm.core.SharedViewModel
import com.hsnozan.basemvvm.model.LongLivedTokenModel
import com.hsnozan.basemvvm.model.Resources
import com.hsnozan.basemvvm.utils.Preference_ApplicationData
import com.hsnozan.basemvvm.utils.SingleLiveEvent
import com.hsnozan.basemvvm.utils.tryCatch
import io.reactivex.disposables.Disposable

class SplashViewModel(
    var baseApplication: BaseApplication,
    var splashRepository: SplashRepository,
    var preference: Preference_ApplicationData
) : SharedViewModel(baseApplication) {

    private var disposable: Disposable? = null
    private var isValidate = false
    val progressLiveData: MutableLiveData<Boolean> = SingleLiveEvent()

    private val tokenObserve = Observer<Resources<LongLivedTokenModel>> { resource ->
        resource?.let {
            when (it) {
                is Resources.Success -> {
                    openHomeActivity()
                }
                is Resources.Error -> {
                    progressLiveData.postValue(false)
                }
                else -> {
                    progressLiveData.postValue(false)
                }
            }
        }
    }

    init {
        checkValidLongLivedToken()
        splashRepository.genericLiveData.observeForever(tokenObserve)
    }

    private fun checkValidLongLivedToken() {
        when {
            preference.accessToken.isNullOrEmpty() -> {
                preference.putIsFirstOpen(true)
                progressLiveData.postValue(false)
            }
            preference.getIsFirstOpen() && preference.longLivedAccessToken.isNullOrEmpty() -> {
                splashRepository.getLongLivedToken()
            }
            else -> {
                preference.putIsFirstOpen(true)
                openHomeActivity()
            }
        }
    }

    private fun openHomeActivity() {
        progressLiveData.postValue(true)
    }

    override fun onCleared() {
        super.onCleared()
        tryCatch(tryBlock = {
            disposable?.takeIf { isValidate }.apply {
                disposable?.dispose()
            }
        })
    }
}