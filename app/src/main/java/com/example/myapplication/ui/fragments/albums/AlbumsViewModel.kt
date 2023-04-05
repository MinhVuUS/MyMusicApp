/*
 * Copyright (c) 2020. By Shahin Montazeri (shahin.montazeri@gmail.com) 
 * Programmed for demonstration purposes
 */

package com.example.myapplication.ui.fragments.albums


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.myapplication.data.models.ResponseData
import com.example.myapplication.data.models.album.Album
import com.example.myapplication.data.remote.Repository
import com.example.myapplication.data.services.ServiceType
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class AlbumsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private var currentQueryValue: String? = null

    /**
     * keep Albums alive in viewmodel until it's changed
     */
    private var currentSearchResult: Flow<PagingData<ResponseData>>? = null

    fun fetchAlbums(artistId: String): Flow<PagingData<ResponseData>> {
        val lastResult = currentSearchResult
        if (artistId == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = artistId
        val newResult: Flow<PagingData<ResponseData>> = repository.fetch(artistId, ServiceType.Albums)
            .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }
}