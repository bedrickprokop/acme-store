package com.acmestore.model.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int,
    var name: String?,
    var email: String?,
    var money: Double,
    var products: List<Product>?
) : Parcelable