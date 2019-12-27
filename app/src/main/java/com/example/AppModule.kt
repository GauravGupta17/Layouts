package com.example

import android.content.Context
import androidx.work.WorkManager
import com.example.layouts.R
import com.example.models.BlurViewModel
import com.example.models.LogInVm
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val vmModule = module {
    viewModel {BlurViewModel()}
    viewModel { LogInVm() }
}

val appModule = module {
   single {WorkManager.getInstance(get())}
}

val firebaseModule = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single { getGoogleClient(get()) }
}



fun getGoogleClient(context:Context):GoogleSignInClient{
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.default_web_client_id)).requestEmail()
        .build()
    return GoogleSignIn.getClient(context, gso)
}



