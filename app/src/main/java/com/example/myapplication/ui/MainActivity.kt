package com.example.myapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.media.session.PlaybackStateCompat
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import androidx.core.view.isVisible


import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.example.myapplication.R
import com.example.myapplication.adapters.SwipeSongAdapter
import com.example.myapplication.data.entities.Song
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.exoplayer.callback.toSong
import com.example.myapplication.exoplayer.isPlaying
import com.example.myapplication.other.Status
import com.example.myapplication.other.Status.SUCCESS
import com.example.myapplication.other.Status.ERROR
import com.example.myapplication.other.Status.LOADING
import com.example.myapplication.ui.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
//BaseActivity(R.layout.activity_main)
//class MainActivity @Inject constructor() : AppCompatActivity()
class MainActivity @Inject constructor(): AppCompatActivity()  {


//    private val viewModel by viewModels<MainViewModel>()
private val viewModel : MainViewModel by viewModels()
    @Inject
    lateinit var swipeSongAdapter: SwipeSongAdapter

    @Inject
    lateinit var glide: RequestManager

    private var currPlayingSong: Song? = null

    private var playbackState: PlaybackStateCompat? = null

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        subscribeToObservers()



        binding.vpSong.adapter = swipeSongAdapter

        binding.vpSong.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (playbackState?.isPlaying == true) {
                    viewModel.playOrToggleSong(swipeSongAdapter.songs[position])
                } else {
                    currPlayingSong = swipeSongAdapter.songs[position]
                }
            }
        })

        binding.ivPlayPause.setOnClickListener {
            currPlayingSong?.let {
                viewModel.playOrToggleSong(it, true)
            }
        }
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.findNavController()
        swipeSongAdapter.setItemClickListener {
            navController.navigate(
                R.id.globalActionToSongFragment
            )
//            navHostFragment.findNavController().navigate(R.id.globalActionToSongFragment)
        }


        navController.addOnDestinationChangedListener{ _, destination, _ ->
            when (destination.id) {
                R.id.songFragment -> {hideBottomBar()}
                R.id.homeFragment -> {showBottomBar()}
                else -> showBottomBar()
            }
        }

    }

    private fun hideBottomBar() {
        binding.vpSong.isVisible = false
        binding.ivCurSongImage.isVisible = false
        binding.ivPlayPause.isVisible = false
    }

    private fun showBottomBar() {
        binding.vpSong.isVisible = true
        binding.ivCurSongImage.isVisible = true
        binding.ivPlayPause.isVisible = true
    }

    private fun switchViewPagerToCurentSong(song: Song) {
        val newIndex = swipeSongAdapter.songs.indexOf(song)
        if (newIndex != -1) {
            binding.vpSong.currentItem = newIndex
            currPlayingSong = song
        }
    }

    private fun subscribeToObservers() {
        viewModel.mediaItems.observe(this) {
            it?.let { result ->
                when (result.status) {
                    Status.SUCCESS -> {
                        result.data?.let { songs ->
                            swipeSongAdapter.songs = songs
                            if (songs.isNotEmpty()) {
                                glide.load((currPlayingSong ?: songs[0].imageUrl))
                                    .into(binding.ivCurSongImage)
                            }
                            switchViewPagerToCurentSong(currPlayingSong ?: return@observe)
                        }
                    }
                    LOADING -> Unit
                    ERROR -> Unit
                }
            }
        }
        viewModel.currentPlayingSong.observe(this) {
            if (it == null) return@observe

            currPlayingSong = it.toSong()
            glide.load(currPlayingSong?.imageUrl).into(binding.ivCurSongImage)
            switchViewPagerToCurentSong(currPlayingSong ?: return@observe)
        }
        viewModel.playbackState.observe(this) {
            playbackState = it
            binding.ivPlayPause.setImageResource(
                if (playbackState?.isPlaying == true) R.drawable.ic_pause else R.drawable.ic_play
            )
        }
        viewModel.isConnected.observe(this) {
            it?.getContentIfNotHandled()?.let { result ->
                when (result.status) {
                    ERROR -> Snackbar.make(
                        binding.rootLayout,
                        result.message ?: "An unknown error occured",
                        Snackbar.LENGTH_LONG
                    ).show()
                    else -> Unit
                }
            }
        }
        viewModel.networkError.observe(this) {
            it?.getContentIfNotHandled()?.let { result ->
                when (result.status) {
                    ERROR -> Snackbar.make(
                        binding.rootLayout,
                        result.message ?: "An unknown error occured",
                        Snackbar.LENGTH_LONG
                    ).show()
                    else -> Unit
                }
            }
        }

    }
}