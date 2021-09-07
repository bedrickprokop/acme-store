package com.acmestore.model.data

import androidx.annotation.NonNull
import androidx.annotation.Nullable

class StateData<T> {

    @get:NonNull
    var status: DataStatus
        private set

    @get:Nullable
    var data: T?
        private set

    @get:Nullable
    var error: Throwable?
        private set

    init {
        status = DataStatus.CREATED
        data = null
        error = null
    }

    fun withSuccess(@NonNull data: T): StateData<T> {
        status = DataStatus.SUCCESS
        this.data = data
        error = null
        return this
    }

    fun withError(@NonNull error: Throwable?): StateData<T> {
        status = DataStatus.ERROR
        data = null
        this.error = error
        return this
    }

    fun withLoading(): StateData<T> {
        status = DataStatus.LOADING
        data = null
        error = null
        return this
    }

    fun withLoadingComplete(): StateData<T> {
        status = DataStatus.COMPLETE
        return this
    }

    enum class DataStatus {
        CREATED, SUCCESS, ERROR, LOADING, COMPLETE
    }
}