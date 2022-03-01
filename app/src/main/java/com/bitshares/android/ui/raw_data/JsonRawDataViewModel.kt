package com.bitshares.android.ui.raw_data

import android.app.Application
import androidx.lifecycle.MutableLiveData
import bitshareskit.objects.JsonSerializable
import com.bitshares.android.ui.base.BaseViewModel

class JsonRawDataViewModel(application: Application) : BaseViewModel(application) {

    val element = MutableLiveData<JsonSerializable>()

    fun setContent(element: JsonSerializable) {
        this.element.value = element
    }
}

