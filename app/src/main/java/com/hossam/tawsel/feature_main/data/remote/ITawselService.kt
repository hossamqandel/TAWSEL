package com.hossam.tawsel.feature_main.data.remote

import com.hossam.tawsel.feature_auth.data.remote.dto.AuthenticationDto
import com.hossam.tawsel.feature_auth.domain.model.Login
import com.hossam.tawsel.feature_home.data.remote.dto.HomeDto
import com.hossam.tawsel.feature_home.domain.model.Cancel
import com.hossam.tawsel.feature_notification.data.remote.dto.NotificationsDto
import com.hossam.tawsel.feature_order_details.data.remote.dto.OrderDetailsDto
import com.hossam.tawsel.feature_orders.domain.model.OrdersDto
import com.hossam.tawsel.feature_profile.data.remote.dto.ProfileDto
import com.hossam.tawsel.feature_profile.domain.model.Profile
import com.hossam.tawsel.feature_profile.domain.model.UpdatePassword
import com.hossam.tawsel.feature_profile.domain.model.UpdateProfile
import retrofit2.http.*

interface ITawselService {


    @POST("driver/updatetoken")
    suspend fun refreshToken(@Body map: Map<String, String>): String

//    @FormUrlEncoded
    @POST("driver/auth/login")
    suspend fun login(@Body login: Login): AuthenticationDto

    @GET("driver/home")
    suspend fun getHome(): HomeDto

    @GET("driver/wallet")
    suspend fun getWallet() //TODO this fun should return wallet object.. ask inst 'Magdy' about model data variables

    @GET("driver/auth/me")
    suspend fun getProfile(
        @Query("phone") phone: String,
        @Query("password") password: String
    ): ProfileDto

    @POST("driver/auth/updateprofile")
    suspend fun updateProfile(@Body updateProfile: UpdateProfile): ProfileDto

    @POST("driver/auth/updatepassword")
    suspend fun updatePassword(@Body updatePassword: UpdatePassword)

    @GET("driver/order/{order_id}")
    suspend fun singleOrder(@Path("order_id") orderId: String): OrderDetailsDto

    @POST("driver/start/{order_id}")
    suspend fun startOrder(@Path("order_id") orderId: String): Boolean

    @POST("driver/cancel")
    suspend fun cancelOrder(@Body cancel: Cancel): Boolean

    @POST("driver/complete/{order_id}")
    suspend fun completeOrder(@Path("order_id") orderId: String): Boolean


    @GET("driver/orders")
    suspend fun getOrders(): OrdersDto

    @GET("driver/notifications")
    suspend fun getNotifications(): NotificationsDto


//    @GET("driver/start/{driver_id}")
//    suspend fun startOrderByDriverId(@Path("driver_id") driverId: String): HomeDto

//    suspend fun cancelOrderByDriverId(@Path("driver_id") driverId: String): HomeDto

//    suspend fun completeOrderByDriverId(@Path("driver_id") driverId: String): HomeDto

}