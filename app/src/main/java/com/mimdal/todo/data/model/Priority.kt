package com.mimdal.todo.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class Priority : Parcelable {
    HIGH,
    MEDIUM,
    LOW
}