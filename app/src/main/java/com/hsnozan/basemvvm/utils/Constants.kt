package com.hsnozan.basemvvm.utils

object Constants{
    const val BASE_URL = "https://api.instagram.com/"
    const val BASE_URL_PROFILE = "https://graph.instagram.com/"
    const val APP_ID_VALUE = "1052959031737575"
    const val CLIENT_SECRET_VALUE = "5bd1c54931434e3b25cab477297bf445"
    const val REDIRECT_URL_VALUE = "https://instagram.com/"

    // ENDPOINT
    private const val CLIENT_ID = "oauth/authorize/?client_id="
    private const val REDIRECT_URI = "&redirect_uri="
    private const val SCOPE_AND_RESPONSE_TYPE = "&scope=user_profile,user_media&response_type=code"

    const val REQUEST_URL = BASE_URL + CLIENT_ID + APP_ID_VALUE + REDIRECT_URI + REDIRECT_URL_VALUE + SCOPE_AND_RESPONSE_TYPE
}