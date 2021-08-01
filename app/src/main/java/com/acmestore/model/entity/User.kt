package com.acmestore.model.entity

import android.os.Parcel
import android.os.Parcelable
import com.acmestore.Consts.ZERO_INT

// Link: data class: https://antonioleiva.com/data-classes-kotlin/
// Link: Parcelable x Serializable: https://stackoverflow.com/a/50114007
data class User(
    val id: Int,
    val name: String?,
    val email: String?,
    val money: Double,
    val products: List<Product>?
) : Parcelable {

    // Constructor that writes the parcel object to this object form. It works like an deserializer function
    constructor(source: Parcel) : this(
        source.readInt(),
        source.readString(),
        source.readString(),
        source.readDouble(),
        arrayListOf<Product>().apply { source.readArrayList(Product::class.java.classLoader) }
    )

    // Method that writes this object in a Parcel object. It works like a serializer function
    override fun writeToParcel(destination: Parcel, flags: Int) {
        destination.writeInt(id)
        destination.writeString(name)
        destination.writeString(email)
        destination.writeDouble(money)
        destination.writeList(products)
    }

    override fun describeContents(): Int {
        return ZERO_INT
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}