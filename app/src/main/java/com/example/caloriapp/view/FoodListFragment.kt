package com.example.caloriapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.caloriapp.adapter.BesinRecyclerAdapter
import com.example.caloriapp.databinding.FragmentFoodListFragmentBinding
import com.example.caloriapp.viewmodel.FoodDetailsViewModel
import com.example.caloriapp.viewmodel.FoodListViewModel

class FoodListFragment : Fragment() {

    private var _binding : FragmentFoodListFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FoodListViewModel
    private val besinRecyclerAdapter = BesinRecyclerAdapter(arrayListOf())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFoodListFragmentBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[FoodListViewModel::class.java]
        viewModel.refreshData()

        binding.besinRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.besinRecyclerView.adapter = besinRecyclerAdapter


        binding.swipeRefresh.setOnRefreshListener {
            binding.besinRecyclerView.visibility = View.GONE
            binding.besinHataMesaji.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
            viewModel.refreshDataInternet().apply {
                binding.swipeRefresh.isRefreshing = false
            }
        }
        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.foods.observe(viewLifecycleOwner){
            binding.besinRecyclerView.visibility = View.VISIBLE
            besinRecyclerAdapter.foodListUpdate(it)
        }

        viewModel.foodError.observe(viewLifecycleOwner){
            if (it){
                binding.besinRecyclerView.visibility = View.GONE
                binding.besinHataMesaji.visibility = View.VISIBLE
            }
            else{
                binding.besinHataMesaji.visibility = View.GONE
            }
        }

        viewModel.foodLoading.observe(viewLifecycleOwner){
            if (it){
                binding.besinRecyclerView.visibility = View.GONE
                binding.besinHataMesaji.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
            }
            else{
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}