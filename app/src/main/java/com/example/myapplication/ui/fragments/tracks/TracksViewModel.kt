/*
 * Copyright (c) 2020. By Shahin Montazeri (shahin.montazeri@gmail.com) 
 * Programmed for demonstration purposes
 */

package com.example.myapplication.ui.fragments.tracks


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertHeaderItem
import com.example.myapplication.data.remote.Repository
import com.example.myapplication.data.models.ResponseData
import com.example.myapplication.data.models.tracks.Track
import com.example.myapplication.data.services.ServiceType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class TracksViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private var currentQueryValue: String? = null

    /**
     * keep Tracks alive in viewmodel until it's changed
     */
    private var currentSearchResult: Flow<PagingData<ResponseData>>? = null

    fun fetchTracks(albumId: String): Flow<PagingData<ResponseData>> {
        val lastResult = currentSearchResult
        if (albumId == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = albumId
        val newResult: Flow<PagingData<ResponseData>> =
            repository.fetch(albumId, ServiceType.Tracks).map {
                it.insertHeaderItem(
                    ResponseData(albumId, Track())
                )
            }.cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }

}