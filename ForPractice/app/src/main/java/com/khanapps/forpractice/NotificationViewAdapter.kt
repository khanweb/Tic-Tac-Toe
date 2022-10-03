package com.khanapps.forpractice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.khanapps.forpractice.databinding.ActivityNotoficationSetBinding

class NotificationViewAdapter : RecyclerView.Adapter<NotificationViewAdapter.MyViewHolder>() {

    class MyViewHolder(val binding : ActivityNotoficationSetBinding  ): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val inflate = LayoutI

    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}