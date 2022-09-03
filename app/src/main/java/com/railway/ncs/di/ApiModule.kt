package com.railway.ncs.di

import android.content.Context
import android.content.SharedPreferences
import android.provider.SyncStateContract
import com.pha.lahore.network.ApiService
import com.railway.ncs.Utils.Constant
import com.railway.ncs.Utils.Constant.Companion.PREF
import com.railway.ncs.network.ApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    @Singleton
    @Provides
    fun provideApiModule(): ApiService {
       // return ApiService.create()
        return ApiClient().create()

    }
    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(Constant.PREF, Context.MODE_PRIVATE)
    }

}
