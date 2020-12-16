package com.mycompany.my.challenge.factories

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.mycompany.my.challenge.datasource.CommentDataSource
import com.mycompany.my.challenge.models.Comment

class CommentDataSourceFactory(private val loadCallback: (Boolean) -> Unit, from: Int, to: Int): DataSource.Factory<Int, Comment>() {

    val mutableLiveData = MutableLiveData<CommentDataSource>()
    private val fromId = from
    private val toId = to

    override fun create(): DataSource<Int, Comment> {
        val commentDataSource = CommentDataSource(loadCallback, fromId, toId)
        mutableLiveData.postValue(commentDataSource)
        return commentDataSource
    }
}