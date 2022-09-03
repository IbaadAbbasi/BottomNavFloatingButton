package com.railway.ncs.di

import android.content.Context
import androidx.room.Room
import com.kpk.eChallan.database.AppDao
import com.railway.ncs.database.NCSDb

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {
    @Singleton
    @Provides
    fun provideUserDao(ncsDb: NCSDb): AppDao = ncsDb.appDao()

    @Provides
    @Singleton
    fun provideUserDatabase(@ApplicationContext context: Context):
            NCSDb = Room.databaseBuilder(context, NCSDb::class.java, "NCSDb").build()

}