package com.hsnozan.basemvvm.ui.login

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.hsnozan.basemvvm.api.BaseService
import com.hsnozan.basemvvm.model.AuthModel
import com.hsnozan.basemvvm.model.Resources
import com.hsnozan.basemvvm.utils.Preference_ApplicationData
import com.hsnozan.basemvvm.utils.SingleLiveEvent
import io.reactivex.schedulers.Schedulers

class LoginRepositoryImpl(
    var baseService: BaseService,
    var preference: Preference_ApplicationData
): LoginRepository {

    private val accessTokenLiveData : MutableLiveData<Resources<AuthModel>> =  SingleLiveEvent()

    @SuppressLint("CheckResult")
    override fun getAccessTokenAsync(
        clientID: String, clientSecret: String,
        type: String, redirectURI: String, code: String
    ) {
        baseService.getAccessTokenAsync(clientID, clientSecret, type, redirectURI, code)
            .subscribeOn(
                Schedulers.io()
            ).subscribe({
                preference.putAccessToken(it.access_token)
                preference.putUserId(it.user_id)
                accessTokenLiveData.postValue(Resources.Success(it))
            },
                { error ->
                    accessTokenLiveData.postValue(Resources.Error())
                }
            )
    }

    override val genericLiveData: MutableLiveData<Resources<AuthModel>>
        get() =  accessTokenLiveData
}