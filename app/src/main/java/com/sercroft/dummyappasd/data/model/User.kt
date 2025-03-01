package com.sercroft.dummyappasd.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "users")
data class User(
    @PrimaryKey val id: Int,
    val name: String,
    @SerializedName("username") val userName: String,
    val email: String,
    val phone: String,
    val website: String
)