
package com.example.myapplication.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.myapplication.data.models.ResponseData
import com.example.myapplication.data.models.album.Album
import com.example.myapplication.data.models.artist.Artist
import com.example.myapplication.data.models.tracks.Track
import com.example.myapplication.data.remote.paging.PagingDataSource
import com.example.myapplication.data.services.ServiceType
import retrofit2.HttpException
import java.io.IOException
class PagingRepository(
    private val query: String,
    private val serviceType: ServiceType,
    private val pagingDataSource: PagingDataSource
) : PagingSource<Int, ResponseData>() {

    companion object {
        private val inMemoryCache =
            mutableListOf<ResponseData>() // (*) we don't care about the object type
        private val queryCache = mutableListOf<String>()
    }

    private val startPage = 0
    private val limit = 25

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResponseData> {
        val position = params.key ?: startPage
        val apiQuery = query
        return try {

            val oldQuery = queryCache.find {
                apiQuery.equals(it, true)
            }

            if (oldQuery != null && position == startPage) {
                val validatedResults = resultsValidatedAndSorted(apiQuery)
                LoadResult.Page(
                    data = validatedResults,
                    prevKey = null,
                    nextKey = null
                )
            } else {
                val response = when (serviceType) {
                    ServiceType.Artists -> pagingDataSource.fetchArtist(apiQuery, position * limit, limit)
                    ServiceType.Albums -> pagingDataSource.fetchAlbum(apiQuery, position * limit, limit)
                    ServiceType.Tracks -> pagingDataSource.fetchTrack(apiQuery, position * limit, limit)
                }
                val body = response.body()!!
                val results = response.body()!!.data

                queryCache.add(apiQuery)

                val shelled = results.map { ResponseData(query = apiQuery, data = it) }

                inMemoryCache.addAll(
                    shelled
                )

                LoadResult.Page(
                    data = if (position * limit <= body.total) shelled else emptyList(),
                    prevKey = if (position == startPage) null else position - 1,
                    nextKey = if (shelled.isEmpty() || position * limit > body.total) null else position + 1
                )
            }

        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    private fun resultsValidatedAndSorted(query: String): List<ResponseData> {
        return inMemoryCache.filter {
            it.query.contains(query, true)
        }
    }

//    override fun invalidate()
//    {
//        super.invalidate()
//        inMemoryCache.clear()
//        queryCache.clear()
//    }

    override fun getRefreshKey(state: PagingState<Int, ResponseData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
    }
}
}
