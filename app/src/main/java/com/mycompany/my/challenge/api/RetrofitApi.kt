package com.mycompany.my.challenge.api

import com.mycompany.my.challenge.models.Comment
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi {

    @GET("comments")
    fun getCommentsByPostId(@Query("postId") postId: Int): Call<MutableList<Comment>>

}