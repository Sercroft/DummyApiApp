package com.sercroft.dummyappasd.data.model

import com.google.gson.annotations.SerializedName

data class Post(
    val id              : Int,
    val name            : String,
    @SerializedName("username")
    val userName        : String,
    val email           : String,
    val phone           : String,
    val website         : String
)
