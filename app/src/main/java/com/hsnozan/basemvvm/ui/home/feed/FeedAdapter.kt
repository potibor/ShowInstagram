package com.hsnozan.basemvvm.ui.home.feed

import android.annotation.SuppressLint
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.hsnozan.basemvvm.R
import com.hsnozan.basemvvm.core.BaseAdapter
import com.hsnozan.basemvvm.databinding.ViewTypeImageFeedBinding
import com.hsnozan.basemvvm.model.MediaTypeModel
import com.hsnozan.basemvvm.utils.toSimpleString


class FeedAdapter(
    var activity: FeedFragment,
    feedList: ArrayList<MediaTypeModel> = arrayListOf()
) : BaseAdapter<MediaTypeModel, ViewTypeImageFeedBinding>() {

    private var simpleExoPlayer: SimpleExoPlayer? = null
    private lateinit var mediaDataSource: DataSource.Factory

    init {
        items = feedList
    }

    override fun createBinding(parent: ViewGroup): ViewTypeImageFeedBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.view_type_image_feed, parent, false
        )
    }

    override fun bind(binding: ViewTypeImageFeedBinding, item: MediaTypeModel?) {
        binding.model = item
        if (item != null) {
            binding.feedItemTime.text = toSimpleString(item.timestamp)
        }
        if (item?.media_type.equals("IMAGE")) {
            changeVisibility(true, binding)
            Glide.with(activity)
                .load(item?.media_url)
                .into(binding.feedItemImage)
        } else {
            changeVisibility(false, binding)

            val fileUrl = Uri.parse(item?.media_url)
            simpleExoPlayer =
                activity.context?.let { SimpleExoPlayer.Builder(activity.requireContext()).build() }
            binding.feedItemVideo.apply {
                player = simpleExoPlayer
                setShutterBackgroundColor(Color.TRANSPARENT)
            }

            mediaDataSource = DefaultDataSourceFactory(
                activity.requireContext(),
                Util.getUserAgent(
                    activity.requireContext(),
                    ""
                )
            )
            val mediaSource =
                ProgressiveMediaSource.Factory(mediaDataSource).createMediaSource(fileUrl)
            with(simpleExoPlayer!!) {
                addListener(object : Player.EventListener {
                    @SuppressLint("SwitchIntDef")
                    override fun onPlayerStateChanged(
                        playWhenReady: Boolean,
                        playbackState: Int
                    ) {
                        when (playbackState) {
                            Player.STATE_ENDED -> {
                            }
                            Player.STATE_BUFFERING -> {
                            }
                            Player.STATE_READY -> {
                            }
                        }
                    }

                })
                prepare(mediaSource, true, true)
            }
            simpleExoPlayer?.playWhenReady = false
        }
    }

    private fun changeVisibility(isImageType: Boolean, binding: ViewTypeImageFeedBinding) {
        binding.feedItemImage.isVisible = isImageType
        binding.feedItemVideo.isVisible = !isImageType
    }


    override fun setData(items: List<MediaTypeModel>) {
        simpleExoPlayer.let {
            it?.release()
        }
        this.items = items.distinct()
        notifyDataSetChanged()
    }
}