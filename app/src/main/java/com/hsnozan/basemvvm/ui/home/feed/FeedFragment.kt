package com.hsnozan.basemvvm.ui.home.feed

import android.os.Bundle
import com.google.android.exoplayer2.SimpleExoPlayer
import com.hsnozan.basemvvm.R
import com.hsnozan.basemvvm.core.BaseFragment
import com.hsnozan.basemvvm.databinding.FeedFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class FeedFragment : BaseFragment<FeedViewModel, FeedFragmentBinding>() {

    private val feedViewModel: FeedViewModel by viewModel()
    private val feedAdapter by lazy { FeedAdapter(this) }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initialize()
        observeFeedModel()
        feedViewModel.getMediaFeed()
    }

    private fun observeFeedModel() {
        feedViewModel.feedModelLiveData.observeForever {
            if (!it.isNullOrEmpty()) {
                feedAdapter.setData(it)
            }
        }
    }

    private fun initialize() {
        binding.viewmodel = viewModel
        binding.feedRecyclerView.adapter = feedAdapter
    }

    override fun getLayoutID(): Int {
        return R.layout.feed_fragment
    }

    override fun initViewModelClass(): Class<FeedViewModel> {
        return feedViewModel.javaClass
    }

}
