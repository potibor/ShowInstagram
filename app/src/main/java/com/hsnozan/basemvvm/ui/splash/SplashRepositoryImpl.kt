package com.hsnozan.basemvvm.ui.splash

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.hsnozan.basemvvm.api.BaseService
import com.hsnozan.basemvvm.model.LongLivedTokenModel
import com.hsnozan.basemvvm.model.Resources
import com.hsnozan.basemvvm.utils.Constants
import com.hsnozan.basemvvm.utils.Preference_ApplicationData
import com.hsnozan.basemvvm.utils.SingleLiveEvent
import io.reactivex.schedulers.Schedulers

class SplashRepositoryImpl(
    var baseService: BaseService,
    var preference: Preference_ApplicationData
) : SplashRepository {

    private val accessTokenLiveData: MutableLiveData<Resources<LongLivedTokenModel>> =
        SingleLiveEvent()

    @SuppressLint("CheckResult")
    override fun getLongLivedToken() {
        baseService.getLongLiveToken(
            "ig_exchange_token",
            Constants.CLIENT_SECRET_VALUE,
            preference.accessToken
        ).subscribeOn(
            Schedulers.io()
        ).subscribe({
            preference.putLongLivedAccessToken(it.access_token)
            preference.putIsFirstOpen(false)
            accessTokenLiveData.postValue(Resources.Success(it))
        },
            { error ->
                accessTokenLiveData.postValue(Resources.Error())
            }
        )
    }

    override val genericLiveData: MutableLiveData<Resources<LongLivedTokenModel>>
        get() = accessTokenLiveData
}