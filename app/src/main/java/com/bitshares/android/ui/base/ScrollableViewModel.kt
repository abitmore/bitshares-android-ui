package com.bitshares.android.ui.base

import android.app.Application
import com.bitshares.android.ui.account.AccountViewModel
import modulon.extensions.livedata.NonNullMutableLiveData

class ScrollableViewModel(application: Application) : AccountViewModel(application) {

    val isOnTop = NonNullMutableLiveData(true)

    fun setOnTop(top: Boolean) {
        if (isOnTop.value != top) isOnTop.value = top
    }

    val scrollY = NonNullMutableLiveData(0f)

}