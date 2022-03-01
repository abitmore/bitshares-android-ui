package com.bitshares.android.ui.main.explore

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.bitshares.android.extensions.viewbinder.logo
import com.bitshares.android.ui.account.picker.AccountPickerViewModel
import com.bitshares.android.ui.account.voting.VotingViewModel
import com.bitshares.android.ui.asset.picker.AssetPickerViewModel
import com.bitshares.android.ui.base.ContainerFragment
import com.bitshares.android.ui.base.*
import com.bitshares.android.ui.main.MainViewModel

class ExploreFragment_Market : ContainerFragment() {

    private val viewModel: ExploreViewModel by activityViewModels()
    private val votingViewModel: VotingViewModel by activityViewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    private val accountSearchingViewModel: AccountPickerViewModel by activityViewModels()
    private val assetSearchingViewModel: AssetPickerViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler {
            logo()
        }
    }
}