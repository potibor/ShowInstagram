package com.hsnozan.basemvvm.model

import java.util.*

data class LongLivedTokenModel(val access_token: String)

data class AuthModel(
    val access_token: String,
    val user_id: String
)

data class UserSelfModel(
    val username: String,
    val id: Long,
    val account_type: String,
    val media_count: Int
)

data class MediaIdBaseModel(val data: Array<MediaIdModel>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MediaIdBaseModel

        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        return data.contentHashCode()
    }
}

data class MediaIdModel(val id: Long)

data class MediaTypeModel(
    val id: Long,
    val media_type: String,
    val media_url: String,
    val username: String,
    val timestamp: Date
)