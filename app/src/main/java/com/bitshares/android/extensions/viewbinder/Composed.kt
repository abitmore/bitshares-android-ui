package com.bitshares.android.extensions.viewbinder

import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.updatePadding
import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import bitshareskit.extensions.formatAssetBalance
import bitshareskit.models.AssetAmount
import bitshareskit.objects.AccountObject
import com.bitshares.android.R
import com.bitshares.android.extensions.text.createAccountSpan
import com.bitshares.android.netowrk.java_websocket.TransactionBuilder
import com.bitshares.android.provider.chain_repo.AssetRepository
import modulon.extensions.graphics.createRoundRectSelectorDrawable
import modulon.extensions.livedata.combineNonNull
import modulon.extensions.livedata.mapSuspend
import modulon.extensions.view.*
import modulon.extensions.viewbinder.cell
import modulon.extensions.viewbinder.spacer
import modulon.extensions.viewbinder.frameLayout
import modulon.layout.recycler.RecyclerLayout
import modulon.layout.recycler.section
import modulon.union.Union
import modulon.widget.PlainTextView

fun ViewGroup.feeCell(union: Union, transactionBuilder: LiveData<TransactionBuilder>) = cell {
    updatePaddingVerticalV6()
    title = context.getString(R.string.transaction_fee_title)
    combineNonNull(
        transactionBuilder.switchMap { it.feeState },
        transactionBuilder.switchMap { it.fee }.mapSuspend { AssetRepository.getAssetAmountDetail(it) }
    ).observe(union.lifecycleOwner) {
        subtitle = when (it.first) {
            TransactionBuilder.FeeState.EMPTY -> context.getString(R.string.transaction_fee_state_empty)
            TransactionBuilder.FeeState.CHECKING -> context.getString(R.string.transaction_fee_state_checking)
            TransactionBuilder.FeeState.COMPLETE -> when {
                it.second == AssetAmount.EMPTY -> context.getString(R.string.transaction_fee_state_empty)
                it.second.amount == 0L -> context.getString(R.string.transaction_fee_state_free_of_charge)
                else -> formatAssetBalance(it.second)
            }
            TransactionBuilder.FeeState.INSUFFICIENT -> context.getString(R.string.transaction_fee_state_insufficient)
        }
    }
}

fun RecyclerLayout.logo() {
    section {
        frameLayout {
            backgroundTintColor = context.getColor(R.color.transparent)
            view<ImageView> {
                imageDrawable = R.drawable.ic_bitshares_logo.contextDrawable().apply {
                    mutate()
                    setTint(context.getColor(R.color.background_cover))
                }
                updatePadding(24.dp, 28.dp, 24.dp, 32.dp)
                setFrameParams(240.dp, 140.dp, gravity = Gravity.CENTER_HORIZONTAL)
            }
        }
    }
    spacer()
}


fun PlainTextView.bindAccountTag(account: AccountObject) {
    setupTag()
    text = createAccountSpan(account)
}


fun PlainTextView.setupTag() {
    updatePadding(left = 1.5.dp, right = 1.5.dp)
    background = createRoundRectSelectorDrawable(context.getColor(R.color.background) and 0x00ffffff xor 0x003a3a3a or 0xff000000.toInt(), 0x00, 4.dpf)
}

