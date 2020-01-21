package com.hsnozan.basemvvm.ui.home.profile

import android.os.Bundle
import com.hsnozan.basemvvm.R
import com.hsnozan.basemvvm.core.BaseFragment
import com.hsnozan.basemvvm.databinding.ProfileFragmentBinding
import com.hsnozan.basemvvm.ui.home.HomeActivity
import kotlinx.android.synthetic.main.profile_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment<ProfileViewModel, ProfileFragmentBinding>() {

    private val profileViewModel: ProfileViewModel by viewModel()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initialize()
        profileViewModel.getUserSelf()
        observeModel()
    }

    private fun observeModel() {
        profileViewModel.userModel.observeForever {
            accountType.text = it.account_type
            userId.text = it.id.toString()
            userName.text = it.username
            mediaCount.text = it.media_count.toString()
        }
    }

    private fun initialize() {
        binding.viewmodel = viewModel
    }

    override fun getLayoutID(): Int {
        return R.layout.profile_fragment
    }

    override fun initViewModelClass(): Class<ProfileViewModel> {
        return profileViewModel.javaClass
    }

}
