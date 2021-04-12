package com.mimdal.todo.data.model

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mimdal.todo.data.model.Priority
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "todo_table")
@Parcelize
data class Todo(
    @ColumnInfo
    var title: String,
    @ColumnInfo
    var priority: Priority,
    @ColumnInfo
    var description: String
) : Parcelable{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    var id: Int? = null
}