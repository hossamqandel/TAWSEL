package com.hossam.tawsel.di

import android.content.Context
import com.hossam.tawsel.feature_orders.presentation.OrdersFilterAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(FragmentComponent::class)
object AdapterModule {

    @Provides
    fun provideOrdersFilterAdapter(@ApplicationContext appContext: Context): OrdersFilterAdapter {
        return OrdersFilterAdapter(appContext)
    }
}