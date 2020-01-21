package com.hsnozan.basemvvm.ui.home.feed

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.hsnozan.basemvvm.BaseApplication
import com.hsnozan.basemvvm.core.SharedViewModel
import com.hsnozan.basemvvm.model.MediaIdBaseModel
import com.hsnozan.basemvvm.model.MediaTypeModel
import com.hsnozan.basemvvm.model.Resources
import com.hsnozan.basemvvm.utils.SingleLiveEvent
import com.hsnozan.basemvvm.utils.tryCatch
import io.reactivex.disposables.Disposable

class FeedViewModel(
    var baseApplication: BaseApplication,
    var feedRepository: FeedRepository
) : SharedViewModel(baseApplication) {

    private var disposable: Disposable? = null
    private var isValidate = false
    val feedModelLiveData: MutableLiveData<ArrayList<MediaTypeModel>> = SingleLiveEvent()
    private val feedModelList: ArrayList<MediaTypeModel> = ArrayList()

    private val feedObserve = Observer<Resources<MediaIdBaseModel>> { resource ->
        resource?.let {
            when (it) {
                is Resources.Success -> {
                    it.data?.data?.forEach { mediaIdModel ->
                        feedRepository.getMediaDetail(mediaIdModel.id)
                    }
                }
                is Resources.Error -> {
                }
                else -> {

                }
            }
        }
    }

    private val mediaTypeModel = Observer<Resources<MediaTypeModel>> { resource ->
        resource?.let {
            when (it) {
                is Resources.Success -> {
                    it.data?.let { typeModel -> feedModelList.add(typeModel) }
                    feedModelList.filter { modelList -> modelList.id != it.data?.id }
                    feedModelLiveData.postValue(feedModelList)
                }
                is Resources.Error -> {
                }
                else -> {

                }
            }
        }
    }

    init {
        (feedRepository as FeedRepositoryImpl).mediaIdLiveData.observeForever(feedObserve)
        (feedRepository as FeedRepositoryImpl).mediaTypeLiveData.observeForever(mediaTypeModel)
    }

    fun getMediaFeed() {
        feedRepository.getFeed("id,caption")
    }

    override fun onCleared() {
        super.onCleared()
        tryCatch(tryBlock = {
            disposable?.takeIf { isValidate }.apply {
                disposable?.dispose()
            }
        })
    }
}