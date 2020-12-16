package com.mycompany.my.challenge.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mycompany.my.challenge.viewmodels.CommentViewModel

class CommentViewModelFactory(lowerBound: Int, hightBound: Int) : ViewModelProvider.NewInstanceFactory() {

    private val lower = lowerBound
    private val high = hightBound

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if(modelClass.isAssignableFrom(CommentViewModel::class.java)){
            return CommentViewModel(lowerBound = lower, highBound = high) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}