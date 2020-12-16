package com.mycompany.my.challenge.datasource

import androidx.paging.PositionalDataSource
import com.mycompany.my.challenge.api.RetrofitClient
import com.mycompany.my.challenge.models.Comment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentDataSource(loadCallback: (Boolean) -> Unit, fromID: Int, toID: Int) : PositionalDataSource<Comment>() {

    private var commentList: MutableList<Comment> = mutableListOf()
    private val load = loadCallback
    private val mToID = toID
    private var currentId = fromID

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Comment>) {
        if(params.startPosition + params.loadSize > commentList.size) return
        load.invoke(true)
        callback.onResult(commentList.subList(params.startPosition, params.startPosition+params.loadSize))
        load.invoke(false)
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Comment>) {
        load.invoke(true)
        getComments(callback)
    }

    fun getComments(callback: LoadInitialCallback<Comment>){

        RetrofitClient().apiService.getCommentsByPostId(currentId).enqueue(object : Callback<MutableList<Comment>>{
            override fun onFailure(call: Call<MutableList<Comment>>, t: Throwable) {

            }
            override fun onResponse(
                call: Call<MutableList<Comment>>,
                response: Response<MutableList<Comment>>
            ) {
                if(currentId < mToID){
                    if(response.body() != null){
                        commentList.addAll(response.body()!!)
                        currentId++
                        getComments(callback)
                    }
                }else{
                    if(response.body() != null){
                        val commentsResponse = response.body()
                        if (commentsResponse != null) {
                            commentList.addAll(commentsResponse)
                        }
                        commentsResponse?.let {
                            if(it.size >= 10)
                                callback.onResult(commentList.subList(0, 10), 0)
                            else
                                callback.onResult(commentList.subList(0, it.size), 0)
                        }
                    }
                    load.invoke(false)
                }


            }
        })
    }
}