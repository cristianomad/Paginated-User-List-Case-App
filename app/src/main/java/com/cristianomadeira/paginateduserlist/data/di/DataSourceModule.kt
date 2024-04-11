package com.cristianomadeira.paginateduserlist.data.di

import android.content.Context
import androidx.room.Room
import com.cristianomadeira.paginateduserlist.data.dataSource.local.database.AppDatabase
import com.cristianomadeira.paginateduserlist.data.dataSource.remote.client.HttpClientProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient = HttpClientProvider()(CIO.create())

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "AppDatabase").build()
}