package com.acmestore.view.vo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class ProductOperation : Parcelable {
    ADD_CART, BUY, SELL
}