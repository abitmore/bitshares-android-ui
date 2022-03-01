package com.bitshares.android.ui.main.explore

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.bitshares.android.R
import com.bitshares.android.chain.CommitteeMember
import com.bitshares.android.extensions.compat.showCommitteeBrowserDialog
import com.bitshares.android.extensions.compat.startAccountBrowser
import com.bitshares.android.extensions.viewbinder.bindCommitteeV3
import com.bitshares.android.extensions.viewbinder.logo
import com.bitshares.android.ui.account.picker.AccountPickerViewModel
import com.bitshares.android.ui.account.voting.VotingViewModel
import com.bitshares.android.ui.asset.picker.AssetPickerViewModel
import com.bitshares.android.ui.base.ContainerFragment
import com.bitshares.android.ui.base.*
import com.bitshares.android.ui.main.MainViewModel
import modulon.component.ComponentCell
import modulon.extensions.view.doOnClick
import modulon.extensions.view.doOnLongClick
import modulon.extensions.view.updatePaddingVerticalHalf
import modulon.layout.recycler.*

class ExploreFragment_Committee : ContainerFragment() {

    private val exploreViewModel: ExploreViewModel by activityViewModels()
    private val votingViewModel: VotingViewModel by activityViewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    private val accountSearchingViewModel: AccountPickerViewModel by activityViewModels()
    private val assetSearchingViewModel: AssetPickerViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler {
            section {
                header = context.getString(R.string.voting_active_committee_member)
                list<ComponentCell, CommitteeMember> {
                    construct { updatePaddingVerticalHalf() }
                    data {
                        bindCommitteeV3(it)
                        doOnClick { startAccountBrowser(it.committee.ownerUid) }
                        doOnLongClick { showCommitteeBrowserDialog(it.committee) }
                    }
                    distinctItemsBy { it.committee.uid }
                    votingViewModel.activeCommitteeMembersFiltered.observe(viewLifecycleOwner) { adapter.submitList(it) }
                }
                votingViewModel.activeCommitteeMembersFiltered.observe(viewLifecycleOwner) { isVisible = it.isNotEmpty() }
            }
            section {
                header = context.getString(R.string.voting_standby_committee_member)
                list<ComponentCell, CommitteeMember> {
                    construct { updatePaddingVerticalHalf() }
                    data {
                        bindCommitteeV3(it)
                        doOnClick { startAccountBrowser(it.committee.ownerUid) }
                        doOnLongClick { showCommitteeBrowserDialog(it.committee) }
                    }
                    distinctItemsBy { it.committee.uid }
                    votingViewModel.standbyCommitteeMembersFiltered.observe(viewLifecycleOwner) { adapter.submitList(it) }
                }
                votingViewModel.standbyCommitteeMembersFiltered.observe(viewLifecycleOwner) { isVisible = it.isNotEmpty() }
            }
            logo()
        }
    }
}