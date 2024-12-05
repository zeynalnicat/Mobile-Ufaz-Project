package com.example.finalprojectufaz.ui.search.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalprojectufaz.databinding.ItemTrackBinding
import com.example.finalprojectufaz.domain.mocks.MockTrack

class TracksListAdapter:RecyclerView.Adapter<TracksListAdapter.ViewHolder>() {
    private var navTo : ()->Unit = {}
    private val callBack = object:DiffUtil.ItemCallback<MockTrack>(){
        override fun areItemsTheSame(oldItem: MockTrack, newItem: MockTrack): Boolean {
           return oldItem===newItem
        }

        override fun areContentsTheSame(oldItem: MockTrack, newItem: MockTrack): Boolean {
            return oldItem==newItem
        }
    }

    private val diffUtil = AsyncListDiffer(this,callBack)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val view = ItemTrackBinding.inflate(LayoutInflater.from(parent.context),parent,false)
      return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return diffUtil.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(diffUtil.currentList[position])
    }


    inner class ViewHolder(private val binding:ItemTrackBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item:MockTrack){
            binding.txtName.text = item.name
            itemView.setOnClickListener{
                navTo()
            }
            Glide.with(itemView)
                .load(item.img)
                .into(binding.img)
        }
    }

    fun submitList(items:List<MockTrack>){
        diffUtil.submitList(items)
    }

    fun setNavFunction(nav:()->Unit){
        this.navTo = {
            nav()
        }
    }


}