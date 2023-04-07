/*
 * Copyright (c) 2020. By Shahin Montazeri (shahin.montazeri@gmail.com) 
 * Programmed for demonstration purposes
 */

package com.example.myapplication.ui.fragments.search


import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.TerminalSeparatorType
import androidx.paging.cachedIn
import androidx.paging.insertHeaderItem
import com.example.myapplication.data.remote.Repository
import com.example.myapplication.data.models.ResponseData
import com.example.myapplication.data.models.artist.Artist
import com.example.myapplication.data.services.ServiceType

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private var currentQueryValue: String? = null

    /**
     * keep Search Results alive in viewmodel until it's changed
     */
    private var currentSearchResult: Flow<PagingData<ResponseData>>? = null

    fun search(queryString: String): Flow<PagingData<ResponseData>> {
        val lastResult = currentSearchResult
        if (queryString == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = queryString
       // val headerItem = ResponseData(queryString, Artist())
        val newResult: Flow<PagingData<ResponseData>> =
            repository.fetch(queryString, ServiceType.Artists).map {
                it.insertHeaderItem(
                    TerminalSeparatorType.SOURCE_COMPLETE,
                    ResponseData(queryString,Artist())
//                   ResponseData(queryString,Artist())
                    //headerItem
                   // ResponseData(queryString, Artist())

                )
            }.cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }
}