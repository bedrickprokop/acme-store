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
    var owner: User?
) : Parcelable

@Parcelize
data class User(
    val id: Int,
    var name: String?,
    var email: String?,
    var money: Double,
    var products: List<Product>?
) : Parcelable