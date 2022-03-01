package com.bitshares.android.ui.main.explore

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import bitshareskit.objects.WorkerObject
import com.bitshares.android.R
import com.bitshares.android.extensions.compat.showWorkerBrowserDialog
import com.bitshares.android.extensions.viewbinder.bindWorkerV3
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

class ExploreFragment_Worker : ContainerFragment() {

    private val exploreViewModel: ExploreViewModel by activityViewModels()
    private val votingViewModel: VotingViewModel by activityViewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    private val accountSearchingViewModel: AccountPickerViewModel by activityViewModels()
    private val assetSearchingViewModel: AssetPickerViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler {
            section {
                header = context.getString(R.string.voting_active_workers)
                list<ComponentCell, WorkerObject> {
                    construct {
                        updatePaddingVerticalHalf()
                    }
                    data {
                        bindWorkerV3(it, true)
                        doOnClick {}
                        doOnLongClick {
                            showWorkerBrowserDialog(it)
                        }
                    }
                    payload { data, payload -> isChecked = (payload as Set<*>).contains(data.voteFor) }
                    distinctItemsBy { it.uid }
                    votingViewModel.activeWorkersFiltered.observe(viewLifecycleOwner) { adapter.submitList(it) }
                }
                votingViewModel.activeWorkersFiltered.observe(viewLifecycleOwner) { isVisible = it.isNotEmpty() }
            }
            section {
                header = context.getString(R.string.voting_standby_workers)
                list<ComponentCell, WorkerObject> {
                    construct { updatePaddingVerticalHalf() }
                    data {
                        bindWorkerV3(it, false)
                        doOnClick {}
                        doOnLongClick { showWorkerBrowserDialog(it) }
                    }
                    payload { data, payload -> isChecked = (payload as Set<*>).contains(data.voteFor) }
                    distinctItemsBy { it.uid }
                    votingViewModel.workerList.observe(viewLifecycleOwner) { adapter.submitList(it) }
                }
                votingViewModel.workerList.observe(viewLifecycleOwner) { isVisible = it.isNotEmpty() }
            }
            logo()
        }
    }
}