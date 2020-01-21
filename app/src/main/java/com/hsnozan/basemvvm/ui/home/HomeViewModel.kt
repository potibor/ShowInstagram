package com.hsnozan.basemvvm.ui.home

import androidx.lifecycle.ViewModel
import com.hsnozan.basemvvm.BaseApplication
import com.hsnozan.basemvvm.api.BaseService
import com.hsnozan.basemvvm.core.SharedViewModel
import com.hsnozan.basemvvm.utils.tryCatch
import io.reactivex.disposables.Disposable

class HomeViewModel (var baseApplication: BaseApplication,
var baseService: BaseService
) : SharedViewModel(baseApplication) {

    private var disposable: Disposable? = null
    private var isValidate = false

    override fun onCleared() {
        super.onCleared()
        tryCatch(tryBlock = {
            disposable?.takeIf { isValidate }.apply {
                disposable?.dispose()
            }
        })
    }
}
