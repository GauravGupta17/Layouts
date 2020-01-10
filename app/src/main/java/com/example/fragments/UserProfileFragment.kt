package com.example.fragments


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.layouts.R
import com.example.models.LogInVm
import com.example.models.PlaylistVm
import kotlinx.android.synthetic.main.fragment_user_profile.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.viewmodel.ext.android.sharedViewModel
import java.lang.Exception
import java.net.URL

class UserProfileFragment : Fragment() {

    private val logInVm by sharedViewModel<LogInVm>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        tvUserName.text = logInVm.getUserName()

        btnSignOut.setOnClickListener {
            logInVm.signOut()
            findNavController().navigate(UserProfileFragmentDirections.actionUserProfileFragmentToLoginFragment())
        }


        CoroutineScope(Dispatchers.Main).launch {
            setUpDisplayPicture()?.let { bitmap ->
                context?.let {
                    Glide.with(it).load(bitmap).apply(RequestOptions.circleCropTransform())
                        .into(ivProfileImage)
                }
            }
        }

    }

    private suspend fun setUpDisplayPicture(): Bitmap? {
        var bitmap: Bitmap? = null
        withContext(Dispatchers.IO) {
            val uri = URL(logInVm.getDisplayPicture())
            try {
                bitmap = BitmapFactory.decodeStream(uri.openConnection().getInputStream())
            } catch (e: Exception) {
               /* Toast.makeText(context, "Display Picture Parsing Failed", Toast.LENGTH_LONG).show()*/
                Log.d(TAG, "$e")

            }
        }
        return bitmap
    }

    companion object {
        const val TAG = "UserprofileFrament"
    }

}