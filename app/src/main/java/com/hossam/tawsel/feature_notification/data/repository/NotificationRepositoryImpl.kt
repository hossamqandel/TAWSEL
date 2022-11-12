package com.hossam.tawsel.feature_notification.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.hossam.tawsel.core.Const
import com.hossam.tawsel.core.Const.ONE_SECOND
import com.hossam.tawsel.core.Resource
import com.hossam.tawsel.feature_main.data.remote.ITawselService
import com.hossam.tawsel.feature_notification.data.remote.dto.NotificationsDto
import com.hossam.tawsel.feature_notification.domain.repository.INotificationRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val api: ITawselService
): INotificationRepository {


    override fun getAllNotifications(): Flow<Resource<NotificationsDto>> = flow {
        try {
            emit(Resource.Loading())
            delay(ONE_SECOND)
            val result = api.getNotifications()
            emit(Resource.Success(result))
            return@flow
        } catch (e: IOException){
            Log.i(TAG, "getAllNotifications: $e")
            emit(Resource.Error(Const.Exception_MESSAGE_IO))
        } catch (e: HttpException) {
            Log.i(TAG, "getAllNotifications: $e")
            emit(Resource.Error(Const.Exception_MESSAGE_HTTP))
        }
    }
}