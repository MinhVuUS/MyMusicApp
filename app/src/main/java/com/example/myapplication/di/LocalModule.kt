package com.example.myapplication.di

import com.example.myapplication.data.local.LocalDataSource
import com.example.myapplication.data.local.LocalDataSourceImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Local Module
 * Providing Local Repository Interfaces
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class LocalModule {

    @Binds
    abstract fun bindLocalDataSource(
        localDataSourceImp: LocalDataSourceImp
    ): LocalDataSource

}