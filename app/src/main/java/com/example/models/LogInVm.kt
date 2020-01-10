package com.example.models

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.provider.Settings.Global.getString
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.layouts.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_email.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import javax.inject.Inject

class LogInVm : ViewModel(), KoinComponent {
    private val firebaseAuth by inject<FirebaseAuth>()
    private val googleSignInClient by inject<GoogleSignInClient>()


    var authState = MutableLiveData<AuthenticationState>()

    init {

        if (firebaseAuth.currentUser!=null){
            authState.value = AuthenticationState.AUTHENTICATED

        }

    }



    fun newUserSignIn(): Intent {
        return googleSignInClient.signInIntent
    }

    fun fireBaseAuthWithGoogle(acc: GoogleSignInAccount) {

        val credential = GoogleAuthProvider.getCredential(acc.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {

            if (it.isSuccessful) {
                authState.value = AuthenticationState.AUTHENTICATED

            } else {
                authState.value = AuthenticationState.UNAUTHENTICATED
            }
        }
    }

    fun signOut() {

        firebaseAuth.signOut()
        googleSignInClient.signOut()
        authState.value = AuthenticationState.SIGNOUT


    }
    fun getDisplayPicture(): String {
        return firebaseAuth.currentUser?.photoUrl.toString()
    }

    fun getUserName():String{
        return firebaseAuth.currentUser?.displayName ?:""
    }


    enum class AuthenticationState {
        UNAUTHENTICATED,
        AUTHENTICATED,
        SIGNOUT
    }
}