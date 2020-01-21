package com.hsnozan.basemvvm.ui.home.profile

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.hsnozan.basemvvm.api.BaseService
import com.hsnozan.basemvvm.model.Resources
import com.hsnozan.basemvvm.model.UserSelfModel
import com.hsnozan.basemvvm.utils.Preference_ApplicationData
import com.hsnozan.basemvvm.utils.SingleLiveEvent
import io.reactivex.schedulers.Schedulers

class ProfileRepositoryImpl(
    var baseService: BaseService,
    var preference: Preference_ApplicationData
) : ProfileRepository {

    private val accessTokenLiveData: MutableLiveData<Resources<UserSelfModel>> = SingleLiveEvent()

    @SuppressLint("CheckResult")
    override fun getUserSelf(fields: String) {
        baseService.getUserSelf(fields).subscribeOn(
            Schedulers.io()
        ).subscribe({
            accessTokenLiveData.postValue(Resources.Success(it))
        },
            { error ->
                accessTokenLiveData.postValue(Resources.Error())
            }
        )
    }

    override val genericLiveData: MutableLiveData<Resources<UserSelfModel>>
        get() = accessTokenLiveData
}