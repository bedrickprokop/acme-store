package com.acmestore.model.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: Int,
    val name: String,
    val unitPrice: Double,
    val description: String?,
    val pictureUrl: String?,
    val owner: User?
) : Parcelable