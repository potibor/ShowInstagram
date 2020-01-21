package com.hsnozan.basemvvm.ui.home.feed

import com.hsnozan.basemvvm.core.BaseRepository
import com.hsnozan.basemvvm.model.MediaIdBaseModel

interface FeedRepository : BaseRepository<MediaIdBaseModel> {

    fun getFeed(fields: String)
    fun getMediaDetail(id: Long?)
}