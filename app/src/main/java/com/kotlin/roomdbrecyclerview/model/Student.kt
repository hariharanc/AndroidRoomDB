package com.kotlin.roomdbrecyclerview.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "studentEntry")
data class Student(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    val id: Int? = null,
    @ColumnInfo(name = "studName")
    var name: String,
    var className: String
): Parcelable