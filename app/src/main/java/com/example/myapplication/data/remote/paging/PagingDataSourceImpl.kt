/*
 * Copyright (c) 2020. By Shahin Montazeri (shahin.montazeri@gmail.com)
 * Programmed for demonstration purposes
 */

package com.example.myapplication.data.remote.paging

import android.os.Parcelable
import com.example.myapplication.data.models.ResponseModel
import com.example.myapplication.data.models.album.Album
import com.example.myapplication.data.models.artist.Artist
import com.example.myapplication.data.models.search.SearchItem
import com.example.myapplication.data.models.tracks.Track
import com.example.myapplication.data.remote.paging.PagingDataSource
import com.example.myapplication.data.services.DeezerApi
import com.example.myapplication.data.services.ServiceType
import retrofit2.Response
import javax.inject.Inject

class PagingDataSourceImpl @Inject constructor(
    private val service: DeezerApi
): PagingDataSource {

    override suspend fun fetchArtist(
        query: String,
        page: Int,
        pageLimit: Int
    ): Response<ResponseModel<Artist>> {
        return service.queryForArtists(
            artistName = query,
            page = page,
            limit = pageLimit)
    }

    override suspend fun fetchAlbum(
        query: String,
        page: Int,
        pageLimit: Int
    ): Response<ResponseModel<Album>> {
        return service.queryForAlbums(
            artistId = query,
            page = page,
            limit = pageLimit)
    }

    override suspend fun fetchTrack(
        query: String,
        page: Int,
        pageLimit: Int
    ): Response<ResponseModel<Track>> {
        return service.queryForTracks(
            albumId = query,
            page = page,
            limit = pageLimit)
    }

}