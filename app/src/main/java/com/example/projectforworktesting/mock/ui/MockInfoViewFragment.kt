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
import com.example.projectforworktesting.databinding.FragmentMockInfoViewBinding
import com.example.projectforworktesting.mock.data.room.DbConnection
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MockInfoViewFragment : Fragment() {

    companion object {
        fun newInstance() = MockInfoViewFragment()
    }

    private lateinit var viewModel: MockInfoViewViewModel
    private var binding: FragmentMockInfoViewBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMockInfoViewBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val room = Room.databaseBuilder(requireContext(), DbConnection::class.java, "db").build()
        viewModel = ViewModelProvider(this, MockInfoViewViewModel(room.entityDao()))[MockInfoViewViewModel::class.java]

        val id = arguments?.getInt("ID")
        viewModel.initWithId(id!!)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.factState.collect{
                    binding?.fact = it
                }
            }
        }

        binding!!.like.setOnClickListener {
            viewModel.changeLike()
        }

        binding?.let { root ->
            root.like.setOnClickListener {
                viewModel.changeLike()
            }

            root.button.setOnClickListener {
                Navigation.findNavController(it).navigate(R.id.action_mockInfoViewFragment_to_mockViewFragment)
            }
        }

    }



    override fun onDestroy() {
        super.onDestroy()
        binding = null;
    }

}