package com.hsnozan.basemvvm.ui.home.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.hsnozan.basemvvm.BaseApplication
import com.hsnozan.basemvvm.core.SharedViewModel
import com.hsnozan.basemvvm.model.Resources
import com.hsnozan.basemvvm.model.UserSelfModel
import com.hsnozan.basemvvm.utils.SingleLiveEvent
import com.hsnozan.basemvvm.utils.tryCatch
import io.reactivex.disposables.Disposable

class ProfileViewModel(
    var baseApplication: BaseApplication,
    var profileRepository: ProfileRepository
) : SharedViewModel(baseApplication) {

    private var disposable: Disposable? = null
    private var isValidate = false
    val userModel: MutableLiveData<UserSelfModel> = SingleLiveEvent()

    private val userObserve = Observer<Resources<UserSelfModel>> { resource ->
        resource?.let {
            when (it) {
                is Resources.Success -> {
                    userModel.postValue(it.data)
                    /*userName = it.data?.username
                    mediaCount = it.data?.media_count.toString()
                    userId = it.data?.id.toString()
                    accountType = it.data?.account_type*/
                }
                is Resources.Error -> {
                }
                else -> {

                }
            }
        }
    }

    init {
        profileRepository.genericLiveData.observeForever(userObserve)
    }

    fun getUserSelf() {
        profileRepository.getUserSelf("username,media_count,id,account_type")
    }

    override fun onCleared() {
        super.onCleared()
        tryCatch(tryBlock = {
            disposable?.takeIf { isValidate }.apply {
                disposable?.dispose()
                profileRepository.genericLiveData.removeObserver(userObserve)
            }
        })
    }
}