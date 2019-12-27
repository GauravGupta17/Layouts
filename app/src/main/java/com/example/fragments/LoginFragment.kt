package com.example.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.RC_SIGN
import com.example.layouts.R
import com.example.models.LogInVm
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel

class LoginFragment : Fragment() {

    private val logInVm by sharedViewModel<LogInVm>()
    private val firebaseAuth by inject<FirebaseAuth>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (firebaseAuth.currentUser != null) {
            findNavController().navigate(R.id.action_loginFragment_to_userDetailsFragment)
        }

        btnGoogleSignIn.setOnClickListener {
            val intent = logInVm.newUserSignIn()
            googleSignIn(intent)
        }

        logInVm.authState.observe(this, androidx.lifecycle.Observer {
            when (it) {
                LogInVm.AuthenticationState.AUTHENTICATED -> findNavController().navigate(R.id.action_loginFragment_to_userDetailsFragment)

                LogInVm.AuthenticationState.SIGNOUT -> Snackbar.make(loginFragment,"Signout",Snackbar.LENGTH_LONG).show()

                else -> Snackbar.make(loginFragment, "login Failed", Snackbar.LENGTH_LONG).show()
            }
        })


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                logInVm.fireBaseAuthWithGoogle(account!!)

            } catch (e: ApiException) {
                Log.e(TAG, "$e")
            }

        }

    }

    private fun googleSignIn(intent: Intent) {
        startActivityForResult(intent, RC_SIGN)
    }

    companion object {
        const val TAG = "LoginFragment"
    }

}