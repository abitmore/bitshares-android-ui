package com.bitshares.android.ui.account.margin

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.bitshares.android.preference.old.Settings
import com.bitshares.android.provider.chain_repo.AssetRepository
import com.bitshares.android.ui.account.AccountViewModel
import modulon.extensions.livedata.filterChildNotNull
import modulon.extensions.livedata.filterNotNull
import modulon.extensions.livedata.map
import modulon.extensions.livedata.mapChildParallel

class MarginPositionViewModel(application: Application) : AccountViewModel(application) {

    val priceUnits = Settings.KEY_MARKET_GROUPS.default.mapChildParallel(viewModelScope) { AssetRepository.getAssetWithBitassetDataOrNull(it) }.filterChildNotNull()
    val priceUnit = Settings.KEY_BALANCE_UNIT.map(viewModelScope) { AssetRepository.getAssetWithBitassetDataOrNull(it) }.filterNotNull()

}

