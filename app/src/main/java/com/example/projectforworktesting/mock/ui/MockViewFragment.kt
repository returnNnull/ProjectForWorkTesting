package com.example.projectforworktesting.mock.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import androidx.room.Room
import com.example.projectforworktesting.R
import com.example.projectforworktesting.databinding.FragmentMockViewBinding
import com.example.projectforworktesting.mock.data.adapters.SportFactDataListAdapter
import com.example.projectforworktesting.mock.data.room.DbConnection
import kotlinx.coroutines.launch

class MockViewFragment : Fragment() {

    companion object {
        fun newInstance() = MockViewFragment()
    }

    private lateinit var viewModel: MockViewViewModel
    private var binding: FragmentMockViewBinding? = null

    private var sportFactDataListAdapter: SportFactDataListAdapter = SportFactDataListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMockViewBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val room = Room.databaseBuilder(requireContext(), DbConnection::class.java, "db").build()
        viewModel = ViewModelProvider(
            this,
            MockViewViewModel(room.entityDao())
        )[MockViewViewModel::class.java]


        sportFactDataListAdapter.itemClick {
            navigateToInfo(view, it.id)
        }

        binding?.let {
            it.recyclerView.adapter = sportFactDataListAdapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.listState.collect {
                    sportFactDataListAdapter.items = it
                }
            }
        }
    }

    private fun navigateToInfo(view: View, id: Int) {
        val bundle = Bundle().also {
            it.putInt("ID", id)
        }
        Navigation.findNavController(view)
            .navigate(R.id.action_mockViewFragment_to_mockInfoViewFragment, bundle)
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}