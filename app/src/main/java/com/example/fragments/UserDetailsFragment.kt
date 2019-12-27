package com.example.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.layouts.R
import com.example.models.LogInVm
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_user_details.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel

class UserDetailsFragment : Fragment() {

    private val firebaseAuth by inject<FirebaseAuth>()
    private val logInVm by sharedViewModel<LogInVm>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val email = firebaseAuth.currentUser?.email ?: ""
        val name = firebaseAuth.currentUser?.displayName ?: ""
        putEmailAndName(email, name)

        btnSubmit.setOnClickListener {
            validateCred(etEmail.text.toString(), etName.text.toString())
        }
        logInVm.authState.observe(this, Observer {
            if (it == LogInVm.AuthenticationState.SIGNOUT) findNavController().navigate(R.id.action_userDetailsFragment_to_loginFragment)

        })

    }

    private fun validateCred(email: String, name: String) {

        if (email.isBlank() && name.isBlank()) {
            Toast.makeText(context, "Enter Credentials", Toast.LENGTH_LONG).show()
            return

        }


    }

    private fun putEmailAndName(email: String, name: String) {
        etEmail.setText(email)
        etName.setText(name)
    }

    companion object {
        const val TAG = "UserDetailsFragment"
    }


}