package com.hsnozan.basemvvm.api

import com.hsnozan.basemvvm.model.*
import io.reactivex.Single
import retrofit2.http.*

interface BaseService {

    @GET("access_token")
    fun getLongLiveToken(
        @Query("grant_type") type: String, @Query("client_secret") clientSecret: String, @Query(
            "access_token"
        ) token: String?
    ): Single<LongLivedTokenModel>

    @FormUrlEncoded
    @POST("/oauth/access_token")
    fun getAccessTokenAsync(
        @Field("client_id") clientID: String,
        @Field("client_secret") clientSecret: String,
        @Field("grant_type") type: String,
        @Field("redirect_uri") redirectURI: String,
        @Field("code") code: String
    ): Single<AuthModel>

    @GET("/me")
    fun getUserSelf(@Query("fields") fields: String): Single<UserSelfModel>

    @GET("me/media")
    fun getMedia(@Query("fields") fields: String): Single<MediaIdBaseModel>

    @GET("{id}")
    fun getMediaDetail(@Path("id") id: Long?, @Query("fields") fields: String): Single<MediaTypeModel>
}