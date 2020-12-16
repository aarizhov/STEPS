package com.mycompany.my.challenge.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mycompany.my.challenge.datasource.CommentDataSource
import com.mycompany.my.challenge.factories.CommentDataSourceFactory
import com.mycompany.my.challenge.models.Comment

class CommentViewModel(lowerBound: Int, highBound: Int) : ViewModel() {
    private var commentList : LiveData<PagedList<Comment>> = MutableLiveData()
    private var mutableLiveData = MutableLiveData<CommentDataSource>()
    private var loadingEvent: MutableLiveData<Boolean> = MutableLiveData()

    init {
        val factory: CommentDataSourceFactory by lazy {
            CommentDataSourceFactory({loadingEvent.postValue(it)}, lowerBound, highBound)
        }
        mutableLiveData = factory.mutableLiveData
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(10)
            .build()
        commentList = LivePagedListBuilder(factory, config).build()
    }

    fun getData() : LiveData<PagedList<Comment>>{
        return commentList
    }
    fun getLoadingEvent(): LiveData<Boolean>{
        return loadingEvent
    }
}
