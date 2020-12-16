package com.mycompany.my.challenge.models

import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("postId")
    val postId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("body")
    val body: String
)