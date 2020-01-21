package com.hsnozan.basemvvm.utils

import com.skydoves.preferenceroom.DefaultPreference
import com.skydoves.preferenceroom.EncryptEntity
import com.skydoves.preferenceroom.KeyName
import com.skydoves.preferenceroom.PreferenceEntity

@DefaultPreference
@EncryptEntity("AZKAZKTOFNATECHX")
@PreferenceEntity
open class ApplicationData {

    @KeyName("isFirstOpen")
    @JvmField val isFirstOpen: Boolean = false

    @KeyName("serverUrl")
    @JvmField val serverUrl: String? = "https://graph.instagram.com/"

    @KeyName("accessToken")
    @JvmField val accessToken: String? = ""

    @KeyName("longLivedAccessToken")
    @JvmField val longLivedAccessToken: String? = ""

    @KeyName("userId")
    @JvmField val userId: String? = ""


}