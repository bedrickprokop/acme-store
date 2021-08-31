package com.acmestore.model.entity

// TODO convert product object to parcelable
class Product(
    val id: Int,
    val name: String,
    val unitPrice: Double,
    val description: String?,
    val pictureUrl: String?,
    val owner: User?
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Product

        if (id != other.id) return false
        if (name != other.name) return false
        if (unitPrice != other.unitPrice) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + unitPrice.hashCode()
        return result
    }
}