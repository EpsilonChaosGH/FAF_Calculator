package com.example.fafcalculator.app.di

import android.content.Context
import androidx.room.Room
import com.example.fafcalculator.core_db.AppDatabase
import com.example.fafcalculator.core_db.dao.ParamsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "FAF_Calculator-DB"
        )
            .createFromAsset("init_db.db")
            .build()

    @Provides
    @Singleton
    fun paramsDao(db: AppDatabase): ParamsDao = db.paramsDao()
}