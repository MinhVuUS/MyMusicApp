package com.example.myapplication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapters.SongAdapter
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.other.Status
import com.example.myapplication.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
private const val TAG = "HOMEFRAGMENT"
@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentHomeBinding


    @Inject
    lateinit var songadapter: SongAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        setupRecyclerView()
        subscribeToObservers()

        songadapter.setOnItemClickListener {
            viewModel.playOrToggleSong(it)
        }

    }

    private fun setupRecyclerView() = binding.rvAllSongs.apply {
        adapter = songadapter
        layoutManager = LinearLayoutManager(requireContext())
    }


    private fun subscribeToObservers() {
        viewModel.mediaItems.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Status.SUCCESS -> {
                    binding.allSongsProgressBar.isVisible = false
                    result.data?.let {
                        songadapter.songs = it

                    }
                }
                Status.ERROR -> Unit
                Status.LOADING -> binding.allSongsProgressBar.isVisible = true
            }
        }
    }

}

//
//@AndroidEntryPoint
//class HomeFragment: Fragment(R.layout.fragment_home) {
//    lateinit var mainViewModel: MainViewModel
//
//    @Inject
//    lateinit var songAdapter: SongAdapter
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
//    }
//
//    private fun subscribeToObservers() {
//        mainViewModel.mediaItems.observe(viewLifecycleOwner){result ->
//            when(result.status) {
//                Status.SUCCESS -> {
//
//                }
//                Status.ERROR -> Unit
//                Status.LOADING -> allSongProgressBar.isVis
//            }
//        }
//    }
//}
//private const val TAG = "HOMEFRAGMENT"
//
//@AndroidEntryPoint
//class HomeFragment: Fragment(R.layout.fragment_home) {
//
//    lateinit var mainViewModel: MainViewModel
//    lateinit var binding: FragmentHomeBinding
//
//    @Inject
//    lateinit var songAdapter: SongAdapter
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        super.onCreateView(inflater, container, savedInstanceState)
//        binding = FragmentHomeBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
//        setupRecyclerView()
//        subscribeToObservers()
//
//        songAdapter.setItemClickListener {
//            mainViewModel.playOrToggleSong(it)
//        }
//    }
//
//    private fun setupRecyclerView() {
//        binding.rvAllSongs.apply {
//            adapter = songAdapter
//            layoutManager = LinearLayoutManager(requireContext())
//        }
//    }
//
//    private fun subscribeToObservers() {
//        mainViewModel.mediaItems.observe(viewLifecycleOwner) { result ->
//            when(result.status) {
//                Status.SUCCESS -> {
//                    binding.allSongsProgressBar.isVisible = false
//                    result.data?.let { songs ->
//                        songAdapter.songs = songs
//                    }
//                }
//                Status.ERROR -> Unit
//                Status.LOADING -> binding.allSongsProgressBar.isVisible = true
//            }
//        }
//    }
//}



//@AndroidEntryPoint
//class HomeFragment: Fragment(R.layout.fragment_home) {
//
//    lateinit var mainViewModel: MainViewModel
//    lateinit var binding: FragmentHomeBinding
//
//    @Inject
//    lateinit var songAdapter: SongAdapter
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
//        setupRecyclerView()
//        subscribeToObservers()
//
//        songAdapter.setItemClickListener {
//            mainViewModel.playOrToggleSong(it)
//        }
//    }
//
//    private fun setupRecyclerView() = binding.rvAllSongs.apply {
//       adapter = songAdapter
//        layoutManager = LinearLayoutManager(requireContext())
//    }
//
//    private fun subscribeToObservers() {
//        mainViewModel.mediaItems.observe(viewLifecycleOwner) { result ->
//            when(result.status) {
//                Status.SUCCESS -> {
//                    binding.allSongsProgressBar.isVisible = false
//                    result.data?.let {songs ->
//                        songAdapter.songs = songs
//                    }
//                }
//                Status.ERROR -> Unit
//                Status.LOADING -> binding.allSongsProgressBar.isVisible = true
//            }
//        }
//    }
//}
