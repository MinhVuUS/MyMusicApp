/*
 * Copyright (c) 2020. By Shahin Montazeri (shahin.montazeri@gmail.com)
 * Programmed for demonstration purposes
 */

package com.example.myapplication.data.services

import com.example.myapplication.data.models.ResponseModel
import com.example.myapplication.data.models.album.Album
import com.example.myapplication.data.models.artist.Artist
import com.example.myapplication.data.models.search.SearchItem
import com.example.myapplication.data.models.tracks.Track
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface DeezerApi {

    /*@GET("search")
    @Headers(
        "No-Locality: true"
    )
    suspend fun generalSearch(
        @Query("q") artistName: String,
        @Query("order") order: String = OrderType.RANKING.name,
        @Query("index") page: Int,
        @Query("limit") limit: Int = 25
    ): Response<ResponseModel<SearchItem>>*/

    @GET("artist/{id}/albums")
    @Headers(
        "No-Locality: true"
    )
    suspend fun queryForAlbums(
        @Path("id") artistId: String? = "",
        @Query("order") order: String = OrderType.RANKING.name,
        @Query("index") page: Int,
        @Query("limit") limit: Int = 25
    ): Response<ResponseModel<Album>>

    @GET("search/artist")
    @Headers(
        "No-Locality: true"
    )
    suspend fun queryForArtists(
        @Query("q") artistName: String,
        @Query("order") order: String = OrderType.RANKING.name,
        @Query("index") page: Int,
        @Query("limit") limit: Int = 25
    ): Response<ResponseModel<Artist>>

    @GET("album/{id}/tracks")
    @Headers(
        "No-Locality: true"
    )
    suspend fun queryForTracks(
        @Path("id") albumId: String? = "",
        @Query("order") order: String = OrderType.RANKING.name,
        @Query("index") page: Int,
        @Query("limit") limit: Int = 25
    ): Response<ResponseModel<Track>>
}