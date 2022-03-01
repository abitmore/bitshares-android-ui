package com.bitshares.android.ui.raw_data

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.bitshares.android.extensions.viewbinder.bindRawData
import com.bitshares.android.extensions.viewbinder.logo
import com.bitshares.android.ui.base.ContainerFragment
import com.bitshares.android.ui.base.*
import modulon.extensions.compat.setClipboardToast
import modulon.extensions.view.doOnLongClick
import modulon.extensions.viewbinder.cell
import modulon.layout.recycler.section

class JsonRawDataFragment : ContainerFragment() {

    private val viewModel: JsonRawDataViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler {
            section {
                cell {
                    viewModel.element.observe(viewLifecycleOwner) {
                        if (it != null) bindRawData(it)
                        viewModel.element.observe(viewLifecycleOwner) {
                            doOnLongClick { setClipboardToast("raw_json", it.toString()) }
                        }
                    }
                }
            }
            logo()
        }
    }

}