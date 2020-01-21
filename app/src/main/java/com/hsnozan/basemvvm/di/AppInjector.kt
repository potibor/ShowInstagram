package com.hsnozan.basemvvm.di

import android.content.Context
import android.os.Environment
import com.google.gson.GsonBuilder
import com.hsnozan.basemvvm.BaseApplication
import com.hsnozan.basemvvm.api.BaseService
import com.hsnozan.basemvvm.ui.home.HomeViewModel
import com.hsnozan.basemvvm.ui.home.feed.FeedRepository
import com.hsnozan.basemvvm.ui.home.feed.FeedRepositoryImpl
import com.hsnozan.basemvvm.ui.home.feed.FeedViewModel
import com.hsnozan.basemvvm.ui.home.profile.ProfileRepository
import com.hsnozan.basemvvm.ui.home.profile.ProfileRepositoryImpl
import com.hsnozan.basemvvm.ui.home.profile.ProfileViewModel
import com.hsnozan.basemvvm.ui.login.LoginRepository
import com.hsnozan.basemvvm.ui.login.LoginRepositoryImpl
import com.hsnozan.basemvvm.ui.login.LoginViewModel
import com.hsnozan.basemvvm.ui.splash.SplashRepository
import com.hsnozan.basemvvm.ui.splash.SplashRepositoryImpl
import com.hsnozan.basemvvm.ui.splash.SplashViewModel
import com.hsnozan.basemvvm.utils.Constants
import com.hsnozan.basemvvm.utils.Preference_ApplicationData
import okhttp3.Cache
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private val baseApplication = BaseApplication()
private val context: Context = BaseApplication.context
private val dispatcher: Dispatcher = Dispatcher()
private val cache = Cache(Environment.getDownloadCacheDirectory(), 10 * 1024 * 1024)
private val okHttpClient: OkHttpClient = provideOkHTTPClient()
private val baseService: BaseService = createRetrofitBuilder(Constants.BASE_URL)
private val baseUserService: BaseService = createRetrofitBuilder(Constants.BASE_URL_PROFILE)


fun provideOkHTTPClient(): OkHttpClient {
    dispatcher.maxRequests = 4
    val appInterceptor = AppInterceptor(Preference_ApplicationData.getInstance(context))
    val logging = HttpLoggingInterceptor().also {
        it.level = HttpLoggingInterceptor.Level.BODY
    }

    return OkHttpClient.Builder()
        .addInterceptor(appInterceptor)
        .addInterceptor(logging)
        .connectTimeout(30, TimeUnit.HOURS)
        .writeTimeout(30, TimeUnit.HOURS)
        .readTimeout(30, TimeUnit.HOURS)
        .dispatcher(dispatcher)
        .cache(cache)
        .build()
}

fun createRetrofitBuilder(baseUrl: String): BaseService {
    val gson = GsonBuilder()
        .setLenient()
        .create()
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(BaseService::class.java)
}

val networkModule = module {
    single { baseService }

}

val repoModule = module {
    single<SplashRepository> {
        SplashRepositoryImpl(
            baseUserService,
            preference = Preference_ApplicationData.getInstance(context)
        )
    }

    single<LoginRepository> {
        LoginRepositoryImpl(
            baseService,
            preference = Preference_ApplicationData.getInstance(context)
        )
    }

    single<ProfileRepository> {
        ProfileRepositoryImpl(
            baseUserService,
            preference = Preference_ApplicationData.getInstance(context)
        )
    }

    single<FeedRepository> {
        FeedRepositoryImpl(
            baseUserService,
            preference = Preference_ApplicationData.getInstance(context)
        )
    }
}

val viewModelModule = module {
    viewModel {
        SplashViewModel(
            baseApplication = baseApplication,
            splashRepository = get() as SplashRepository,
            preference = Preference_ApplicationData.getInstance(context)
        )
    }
    viewModel {
        LoginViewModel(
            baseApplication = baseApplication,
            loginRepository = get() as LoginRepository
        )
    }
    viewModel {
        HomeViewModel(
            baseApplication = baseApplication,
            baseService = baseService
        )
    }
    viewModel {
        FeedViewModel(
            baseApplication = baseApplication,
            feedRepository = get() as FeedRepository
        )
    }
    viewModel {
        ProfileViewModel(
            baseApplication = baseApplication,
            profileRepository = get() as ProfileRepository
        )
    }
}
