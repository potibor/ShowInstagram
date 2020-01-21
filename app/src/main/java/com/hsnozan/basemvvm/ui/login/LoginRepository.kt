package com.hsnozan.basemvvm.ui.login

import com.hsnozan.basemvvm.core.BaseRepository
import com.hsnozan.basemvvm.model.AuthModel

interface LoginRepository: BaseRepository<AuthModel> {
    fun getAccessTokenAsync(clientID: String, clientSecret: String,
                            type: String, redirectURI: String, code: String)
}