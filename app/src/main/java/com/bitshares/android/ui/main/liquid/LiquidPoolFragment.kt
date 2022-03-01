package com.bitshares.android.ui.main.liquid

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.bitshares.android.extensions.viewbinder.logo
import com.bitshares.android.ui.base.ContainerFragment
import com.bitshares.android.ui.base.*
import com.bitshares.android.ui.main.MainViewModel
import modulon.extensions.viewbinder.cell
import modulon.extensions.viewbinder.recyclerLayout
import modulon.extensions.viewbinder.tabLayout
import modulon.layout.recycler.section
import modulon.layout.tab.tab

class LiquidPoolFragment : ContainerFragment() {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupVertical {
            tabLayout {
                tab { text = "Pool" }
            }
            recyclerLayout {
                section {
                    cell {
                        title = "COMING SOON"
                    }
                }
                logo()
            }
        }
    }


}