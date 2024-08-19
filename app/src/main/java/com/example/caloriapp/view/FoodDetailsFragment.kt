package com.example.caloriapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.caloriapp.databinding.FragmentFoodDetailsBinding
import com.example.caloriapp.util.downloadImage
import com.example.caloriapp.util.placeHolderProgressBar
import com.example.caloriapp.viewmodel.FoodDetailsViewModel

class FoodDetailsFragment : Fragment() {
    
    private var _binding : FragmentFoodDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : FoodDetailsViewModel
    var foodID = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFoodDetailsBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[FoodDetailsViewModel::class.java]

        arguments?.let {
            foodID = FoodDetailsFragmentArgs.fromBundle(it).besinID
        }

        viewModel.getRoomData(foodID)

        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.foodLiveData.observe(viewLifecycleOwner){
            binding.besinDetayIsimtextView.text = it.besinIsim
            binding.besinDetayYagtextView.text = it.besinyag
            binding.besinDetayKaloritextView.text = it.besinKalori
            binding.besinDetayKarbonhidrattextView.text = it.besinKarbonhidrat
            binding.besinDetayProteintextView.text = it.besinProtein
            binding.besinDetayImageView.downloadImage(it.besinGorsel, placeHolderProgressBar(requireContext()))
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}