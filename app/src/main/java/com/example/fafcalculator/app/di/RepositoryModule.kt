package com.example.fafcalculator.app.di


import com.example.fafcalculator.core_data.Repository
import com.example.fafcalculator.core_data.RepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindRepository(
        RepositoryImpl: RepositoryImpl
    ): Repository
}