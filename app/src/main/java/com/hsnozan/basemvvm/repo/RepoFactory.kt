package com.hsnozan.basemvvm.repo

import android.app.Application
import com.hsnozan.basemvvm.api.BaseService
import com.hsnozan.basemvvm.core.BaseRepo
import com.hsnozan.basemvvm.ui.login.LoginRepository
import java.util.concurrent.Executors

class RepoFactory(val baseService: BaseService, val application : Application){

    inline fun <reified T : BaseRepo> createInstance(): BaseRepo = when (T::class) {
        else -> throw IllegalArgumentException()
    }
}