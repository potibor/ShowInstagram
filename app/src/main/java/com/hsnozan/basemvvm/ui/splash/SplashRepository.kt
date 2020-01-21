package com.hsnozan.basemvvm.ui.splash

import com.hsnozan.basemvvm.core.BaseRepository
import com.hsnozan.basemvvm.model.LongLivedTokenModel

interface SplashRepository: BaseRepository<LongLivedTokenModel> {
    fun getLongLivedToken()
}