package com.hsnozan.basemvvm.ui.login

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.hsnozan.basemvvm.BaseApplication
import com.hsnozan.basemvvm.core.SharedViewModel
import com.hsnozan.basemvvm.model.AuthModel
import com.hsnozan.basemvvm.model.Resources
import com.hsnozan.basemvvm.utils.Constants
import com.hsnozan.basemvvm.utils.SingleLiveEvent
import com.hsnozan.basemvvm.utils.tryCatch
import io.reactivex.disposables.Disposable
import timber.log.Timber

class LoginViewModel(
    var baseApplication: BaseApplication,
    var loginRepository: LoginRepository
) : SharedViewModel(baseApplication) {

    var accessCode = ""
    var progressVisibility = ObservableField(false)
    private var disposable: Disposable? = null
    private var isValidate = false
    val progressLiveData: MutableLiveData<AuthModel> = SingleLiveEvent()

    private val authObserve = Observer<Resources<AuthModel>> { resource ->
        resource?.let {
            when (it) {
                is Resources.Success -> {
                    if (it.data != null) {
                        progressLiveData.postValue(it.data)
                    }
                    Timber.w("${it.data?.user_id}")
                    Timber.w("$${it.data?.access_token}")
                }
                is Resources.Error -> {
                }
                else -> {
                    Timber.w("AuthModel is not succeded")
                }
            }
        }
    }

    init {
        loginRepository.genericLiveData.observeForever(authObserve)
    }

    fun getAccessToken() {
        loginRepository.getAccessTokenAsync(
            Constants.APP_ID_VALUE,
            Constants.CLIENT_SECRET_VALUE,
            "authorization_code",
            Constants.REDIRECT_URL_VALUE,
            accessCode
        )
    }

    override fun onCleared() {
        super.onCleared()
        tryCatch(tryBlock = {
            disposable?.takeIf { isValidate }.apply {
                disposable?.dispose()
                loginRepository.genericLiveData.removeObserver(authObserve)
            }
        })
    }
}
