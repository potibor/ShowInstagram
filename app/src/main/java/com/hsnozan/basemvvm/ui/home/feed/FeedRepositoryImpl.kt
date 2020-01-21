package com.hsnozan.basemvvm.ui.home.feed

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.hsnozan.basemvvm.api.BaseService
import com.hsnozan.basemvvm.model.MediaIdBaseModel
import com.hsnozan.basemvvm.model.MediaIdModel
import com.hsnozan.basemvvm.model.MediaTypeModel
import com.hsnozan.basemvvm.model.Resources
import com.hsnozan.basemvvm.utils.Preference_ApplicationData
import com.hsnozan.basemvvm.utils.SingleLiveEvent
import io.reactivex.schedulers.Schedulers

@SuppressLint("CheckResult")
class FeedRepositoryImpl(
    var baseService: BaseService,
    var preference: Preference_ApplicationData
) : FeedRepository {

    val mediaIdLiveData: MutableLiveData<Resources<MediaIdBaseModel>> =
        SingleLiveEvent()
    val mediaTypeLiveData: MutableLiveData<Resources<MediaTypeModel>> =
        SingleLiveEvent()


    override val genericLiveData: MutableLiveData<Resources<MediaIdBaseModel>>
        get() = mediaIdLiveData


    override fun getFeed(fields: String) {
        baseService.getMedia(fields)
            .subscribeOn(
                Schedulers.io()
            ).subscribe({
                mediaIdLiveData.postValue(Resources.Success(it))
            },
                { error ->
                    mediaIdLiveData.postValue(Resources.Error(errorMessage = error.message))
                }
            )
    }

    override fun getMediaDetail(id: Long?) {
        baseService.getMediaDetail(id, "id,media_type,media_url,username,timestamp")
            .subscribeOn(
                Schedulers.io()
            ).subscribe({
                mediaTypeLiveData.postValue(Resources.Success(it))
            },
                { error ->
                    mediaTypeLiveData.postValue(Resources.Error(errorMessage = error.message))
                }
            )
    }
}