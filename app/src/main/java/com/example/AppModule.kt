package com.example

import android.content.Context
import androidx.work.WorkManager
import com.example.Repos.FirebaseRepo
import com.example.Repos.RetrofitRepo
import com.example.layouts.R
import com.example.models.BlurViewModel
import com.example.models.LogInVm
import com.example.models.PlaylistVm
import com.example.models.RetrofitVm
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

val vmModule = module {
    viewModel { BlurViewModel() }
    viewModel { LogInVm() }
    viewModel { PlaylistVm(get(), get()) }
    viewModel { RetrofitVm(get()) }
}

val appModule = module {
    single { WorkManager.getInstance(get()) }
}

val firebaseModule = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single { getGoogleClient(get()) }
    single { FirebaseRepo(get()) }
}
val retrofitModule = module {
    single { getOkHttp() }
    single { getRetrofit(get()) }
    single { RetrofitRepo(get()) }
}

fun getGoogleClient(context: Context): GoogleSignInClient {
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.default_web_client_id)).requestEmail()
        .build()
    return GoogleSignIn.getClient(context, gso)
}

fun getOkHttp(): OkHttpClient {

    val cache = Cache(appctx.cacheDir, 1024 * 1024 * 4)

    return OkHttpClient.Builder().cache(cache).addNetworkInterceptor { chain ->
        val cc = CacheControl.Builder().apply {
            maxStale(2, TimeUnit.DAYS)
        }.build()

        val request = chain.request().newBuilder().cacheControl(cc).build()
        chain.proceed(request)

    }.addInterceptor { chain ->
        try {
            chain.proceed(chain.request())

        } catch (e: Exception) {
            val cc = CacheControl.Builder().apply {
                onlyIfCached()
                maxStale(1, TimeUnit.DAYS)
            }.build()
            val offlineRequest = chain.request().newBuilder().cacheControl(cc).build()
            chain.proceed(offlineRequest)
        }

    }.build()
}

fun getRetrofit(httpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(API_BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create()).client(
            httpClient
        ).build()
}

