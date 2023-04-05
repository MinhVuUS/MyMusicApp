package com.example.myapplication.di

import com.example.myapplication.data.remote.Repository
import com.example.myapplication.data.remote.RepositoryImpl
import com.example.myapplication.data.remote.paging.PagingDataSource
import com.example.myapplication.data.remote.paging.PagingDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindPagingSource(
        pagingDataSourceImpl: PagingDataSourceImpl
    ): PagingDataSource

    @Binds
    abstract fun bindRepository(
        repositoryImpl: RepositoryImpl
    ): Repository

}