package com.hsnozan.basemvvm.ui.home

import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.hsnozan.basemvvm.R
import com.hsnozan.basemvvm.core.BaseActivity
import com.hsnozan.basemvvm.databinding.HomeFragmentBinding
import kotlinx.android.synthetic.main.home_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeActivity : BaseActivity<HomeViewModel, HomeFragmentBinding>() {

    private val homeViewModel: HomeViewModel by viewModel()

    companion object {
        fun newInstance() = HomeActivity()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bottomNavigation.setupWithNavController(
            Navigation.findNavController(
                this,
                R.id.host_fragment
            )
        )

        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            onNavDestinationSelected(item, Navigation.findNavController(this, R.id.host_fragment))
        }
        initialize()
    }

    private fun initialize() {
        binding.viewmodel = viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.home_fragment
    }

    override fun initViewModelClass(): Class<HomeViewModel> {
        return homeViewModel.javaClass
    }

}
