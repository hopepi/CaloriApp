package com.example.caloriapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.caloriapp.databinding.BesinRecyclerRowBinding
import com.example.caloriapp.model.Besin
import com.example.caloriapp.util.downloadImage
import com.example.caloriapp.util.placeHolderProgressBar
import com.example.caloriapp.view.FoodListFragmentDirections

class BesinRecyclerAdapter(val foodList : ArrayList<Besin>) : RecyclerView.Adapter<BesinRecyclerAdapter.besinViewHolder>(){

    class besinViewHolder(val binding : BesinRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): besinViewHolder {
        val binding = BesinRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return besinViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    override fun onBindViewHolder(holder: besinViewHolder, position: Int) {
        holder.binding.isimTextView.text = foodList[position].besinIsim
        holder.binding.caloriTextView.text = foodList[position].besinKalori

        holder.itemView.setOnClickListener{
            val action = FoodListFragmentDirections.actionFoodListFragmentToFoodDetailsFragment(foodList[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }

        holder.binding.besinImageView.downloadImage(foodList[position].besinGorsel,
            placeHolderProgressBar(holder.itemView.context))
    }

    fun foodListUpdate(newFoodList : List<Besin>){
        foodList.clear()
        foodList.addAll(newFoodList)
        notifyDataSetChanged()
    }

}