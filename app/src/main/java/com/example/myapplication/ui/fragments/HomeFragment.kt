package com.example.myapplication.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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
        val menuHost: MenuHost = requireActivity()
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        setupRecyclerView()
        subscribeToObservers()

        songadapter.setOnItemClickListener {
            viewModel.playOrToggleSong(it)
        }
//        binding.imageButton.setOnClickListener {
//            findNavController().navigate(R.id.action_homeFragment_to_fragmentSearch)
//        }
        menuHost.addMenuProvider(object :MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu,menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
               return when (menuItem.itemId) {
                   R.id.menu_item_search -> {
                       val controller = findNavController()
               controller.navigate(R.id.action_homeFragment_to_fragmentSearch)
                   true
                   }
                   else -> false
               }
            }
        },viewLifecycleOwner, Lifecycle.State.RESUMED)



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
    }}
//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.menu, menu)
//        super.onCreateOptionsMenu(menu, inflater)
//    }
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when(item.itemId)
//        {
//            R.id.menu_item_search -> {
//                val controller = findNavController()
//                controller.navigate(R.id.action_homeFragment_to_fragmentSearch)
//            }
//        }
//
//        return super.onOptionsItemSelected(item)
//    }
//}

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
