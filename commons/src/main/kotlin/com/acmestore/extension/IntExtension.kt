package com.acmestore.extension

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue

fun Int.dimenToPx(context: Context? = null): Int {
    return context?.resources?.getDimensionPixelSize(this) ?: Resources.getSystem()
        .getDimensionPixelSize(this)
}

fun Int.spToPx(context: Context? = null): Int {
    val display = context?.resources?.displayMetrics ?: Resources.getSystem().displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this.toFloat(), display).toInt()
}