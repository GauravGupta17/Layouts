package com.example.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adapters.GenAdapter
import com.example.adapters.GenAdapter2
import com.example.dialogs.DeleteDialog
import com.example.layouts.R
import com.example.models.LogInVm
import com.example.models.PlaylistVm
import com.example.models.SongInfo
import com.example.utilis.randomColor
import com.google.android.gms.dynamic.SupportFragmentWrapper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_conditional_navigation.*
import kotlinx.android.synthetic.main.fragment_song_list.*
import kotlinx.android.synthetic.main.song_view.*
import kotlinx.android.synthetic.main.song_view.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.viewmodel.ext.android.sharedViewModel
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

class SongListFragment : Fragment() {

    private val logInVm by sharedViewModel<LogInVm>()
    private val playlistVm by sharedViewModel<PlaylistVm>()
    private lateinit var viewAdapter: GenAdapter<SongInfo, SongVH>
    val dialogFragment = DeleteDialog()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_song_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.btmNavSongs?.visibility = View.VISIBLE
        logInVm.authState.observe(this, androidx.lifecycle.Observer {
            when (it) {
                LogInVm.AuthenticationState.SIGNOUT -> findNavController().navigate(R.id.action_songListFragment_to_loginFragment)
                else -> println("signout failed")
            }
        })

            inflateRV(playlistVm.getSongList())

    }

    class SongVH(view: View) : RecyclerView.ViewHolder(view)

    private  fun inflateRV(list: ArrayList<SongInfo>) {


        viewAdapter = GenAdapter(list, { SongVH(it) }, R.layout.song_view, { position, holder ->
            holder.itemView.tvSongName.text = list[position].songName
            holder.itemView.ivFav.setOnClickListener {
                holder.itemView.ivFav?.setImageResource(R.drawable.ic_favorite_black_24dp)
                Toast.makeText(context,"change color",Toast.LENGTH_LONG).show()
            }
            holder.itemView.ivOptions.setOnClickListener {
                fragmentManager?.let {
                    dialogFragment.show(it,"missiles")
                }

            }


        })
        rvUserSongList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewAdapter

        }
    }



}