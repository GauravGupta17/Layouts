package com.example.layouts

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.activity_email.*
import java.util.concurrent.TimeUnit

class EmailActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var callBack: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var storedVerificationId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email)

        auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        callBack = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(phoneCredential: PhoneAuthCredential) {
                signInWithPhoneCredential(phoneCredential)
                /*btnPhoneVerification.visibility = View.VISIBLE
                progress_bar.visibility =View.GONE*/
            }

            override fun onVerificationFailed(e: FirebaseException) {

                if (e is FirebaseAuthInvalidCredentialsException) {

                    Snackbar.make(emailActvity, "invalid number", Snackbar.LENGTH_SHORT).show()
                }
                if (e is FirebaseTooManyRequestsException) {
                    Snackbar.make(emailActvity, "too many request", Snackbar.LENGTH_SHORT).show()
                }

                /*btnPhoneVerification.visibility = View.VISIBLE
                progress_bar.visibility =View.GONE*/
            }

            override fun onCodeSent(code: String, p1: PhoneAuthProvider.ForceResendingToken) {
                storedVerificationId = code

            }

        }



        btnSignUp.setOnClickListener {
            if (etEmail.text?.isEmpty() == true && etPassword.text?.isEmpty() == true) {
                Toast.makeText(this, "Email and PassWord", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {

                signIn(etEmail.text.toString(), etPassword.text.toString())
            }

        }

        btnGoogleSignIn.setOnClickListener {
            signInByGoogle()
        }

        btnPhoneVerification.setOnClickListener {
            signInByNumber()

        }

        btnVerifyCode.setOnClickListener {

            val credential = PhoneAuthProvider.getCredential(storedVerificationId,etCode.text.toString())

            signInWithPhoneCredential(credential)
        }


    }

    /* Email Password  Signing*/
    private fun signIn(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) {

            if (it.isSuccessful) {
                FirebaseAuth.getInstance().currentUser?.sendEmailVerification()
                    ?.addOnCompleteListener {
                        if (it.isSuccessful)
                            Toast.makeText(this, "mail send", Toast.LENGTH_SHORT).show()
                    }

                /*startActivity(Intent(this,PlayListActivity::class.java))*/
            } else
                Toast.makeText(this, "Acccont Creation Failed", Toast.LENGTH_SHORT).show()

        }

    }

    /* Google Signing*/
    private fun signInByGoogle() {
        val intent = googleSignInClient.signInIntent
        startActivityForResult(intent, RC_SIGN_IN)
    }

    private fun fireBaseAuthWithGoogle(acc: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acc.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful)
                Snackbar.make(emailActvity, "Logged In", Snackbar.LENGTH_SHORT).show()
            else
                Snackbar.make(emailActvity, "Logged In Failed", Snackbar.LENGTH_SHORT).show()
        }


    }

    /*Phone Number Signing*/
    private fun signInByNumber() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            "+91${etPhoneNumber.text.toString()}", 15, TimeUnit.SECONDS, this, callBack
        )
        /*btnPhoneVerification.visibility = View.GONE
        progress_bar.visibility = View.VISIBLE*/
    }

    private fun signInWithPhoneCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential).addOnCompleteListener {

            if (it.isSuccessful) {
                Snackbar.make(emailActvity, "Sign In Sucessful", Snackbar.LENGTH_SHORT).show()
            } else {
                Snackbar.make(emailActvity, "Sign In Failed", Snackbar.LENGTH_SHORT).show()
            }

        }


    }

    override fun onStart() {
        super.onStart()


        if (auth.currentUser != null) {

            /*  startActivity(Intent(this,PlayListActivity::class.java))*/

            Toast.makeText(this, "User Logged", Toast.LENGTH_SHORT).show()
            /*updateUI(auth.currentUser)
             */
        } else {

            Toast.makeText(this, "Input Email password", Toast.LENGTH_SHORT).show()

        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                fireBaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                Log.d(TAG, "Failed ApiException")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.playlist_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {

        R.id.signOut -> {

            if (FirebaseAuth.getInstance().currentUser != null)
                Snackbar.make(emailActvity, "Signed Out", Snackbar.LENGTH_SHORT).show()

            FirebaseAuth.getInstance().signOut()
            googleSignInClient.signOut()
            true
        }

        else -> super.onOptionsItemSelected(item)
    }

    companion object {

        private const val RC_SIGN_IN = 9001
        private const val TAG = "EmailActivity"
    }


}