/*
 * Copyright (c) 2020. By Shahin Montazeri (shahin.montazeri@gmail.com)
 * Programmed for demonstration purposes
 */

package com.example.myapplication.data.remote.paging

import com.example.myapplication.data.models.ResponseModel

import com.example.myapplication.data.models.album.Album
import com.example.myapplication.data.models.artist.Artist
import com.example.myapplication.data.models.search.SearchItem
import com.example.myapplication.data.models.tracks.Track
import com.example.myapplication.data.services.ServiceType
import retrofit2.Response

interface PagingDataSource {
//    suspend fun generalSearch(query: String, page : Int, pageLimit: Int) : Response<ResponseModel<SearchItem>>
    suspend fun fetchArtist(query: String, page : Int, pageLimit: Int) : Response<ResponseModel<Artist>>
    suspend fun fetchAlbum(query: String, page : Int, pageLimit: Int) : Response<ResponseModel<Album>>
    suspend fun fetchTrack(query: String, page : Int, pageLimit: Int) : Response<ResponseModel<Track>>
}