/*
 * Copyright (c) 2020. By Shahin Montazeri (shahin.montazeri@gmail.com)
 * Programmed for demonstration purposes
 */

package com.example.myapplication.data.remote

import android.os.Parcelable
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.myapplication.data.local.LocalDataSource
import com.example.myapplication.data.models.ResponseData
import com.example.myapplication.data.remote.paging.PagingDataSource
import com.example.myapplication.data.remote.paging.PagingRepository
import com.example.myapplication.data.services.ServiceType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val pagingDataSource: PagingDataSource
): Repository {
    override fun fetch(query: String, serviceType: ServiceType): Flow<PagingData<ResponseData>> {
        return Pager(
            config = PagingConfig(
                pageSize = 25,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                PagingRepository(
                    query,
                    serviceType,
                    pagingDataSource
                )
            }
        ).flow
    }
}