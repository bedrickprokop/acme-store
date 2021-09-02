package com.acmestore.model.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int,
    val name: String?,
    val email: String?,
    val money: Double,
    val products: List<Product>?
) : Parcelable