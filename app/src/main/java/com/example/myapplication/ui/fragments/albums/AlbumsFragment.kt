/*
 * Copyright (c) 2020. By Shahin Montazeri (shahin.montazeri@gmail.com) 
 * Programmed for demonstration purposes
 */

package com.example.myapplication.ui.fragments.albums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.paging.map
import com.example.myapplication.R
import com.example.myapplication.commons.MyLoadStateAdapter
import com.example.myapplication.data.models.album.Album
import com.example.myapplication.databinding.FragmentAlbumsBinding
import com.example.myapplication.ui.fragments.BaseFragment
import com.example.myapplication.ui.fragments.albums.adapter.AlbumsAdapter
import com.example.myapplication.ui.fragments.albums.AlbumsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AlbumsFragment : BaseFragment<FragmentAlbumsBinding>(R.layout.fragment_albums) {

    private val viewModel: AlbumsViewModel by viewModels()
    private val args: AlbumsFragmentArgs by navArgs()
    private lateinit var albumsAdapter: AlbumsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        setupList()
        fetchAlbums()
    }

    private fun setupList() {
        albumsAdapter = AlbumsAdapter(args.artistName) {
            navigateToTracks(it)
        }
        binding.recyclerView.adapter =
            albumsAdapter.withLoadStateFooter(footer = MyLoadStateAdapter())

        albumsAdapter.loadStateFlow.onEach { state ->
            if (isAdded) {
                binding.loading.isVisible = state.refresh is LoadState.Loading
            }
        }.launchIn(lifecycleScope)
    }

    private fun fetchAlbums() {
        lifecycleScope.launch {
            viewModel.fetchAlbums(args.artistId ?: "").collectLatest { pagingData ->
                if (isAdded) {
                    binding.loading.isVisible = false
                    if (::albumsAdapter.isInitialized) {
                        albumsAdapter.submitData(pagingData.map { it.data as Album })
                    }
                }
            }
        }
    }

    private fun navigateToTracks(album: Album) {
//        findNavController().navigate(
//            AlbumsFragmentDirections.actionFragmentAlbumsToFragmentTracks(
//                albumName = album.title,
//                artistName = album.artist?.name ?: args.artistName,
//                cover = album.coverMedium,
//                albumId = album.id
//            )
//        )
        val controller = findNavController()
        controller.navigate(R.id.action_albumsFragment_to_tracksFragment)
    }

    private fun onBackPressed() {
        findNavController().popBackStack()
    }
}