package com.hsnozan.basemvvm.ui.home.profile

import com.hsnozan.basemvvm.core.BaseRepository
import com.hsnozan.basemvvm.model.UserSelfModel

interface ProfileRepository: BaseRepository<UserSelfModel> {
    fun getUserSelf(fields: String)
}