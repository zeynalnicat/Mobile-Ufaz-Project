package com.example.finalprojectufaz.ui.search.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalprojectufaz.databinding.ItemTrackBinding
import com.example.finalprojectufaz.domain.track.TrackResponseModel

class TracksListAdapter:RecyclerView.Adapter<TracksListAdapter.ViewHolder>() {
    private var navTo : (TrackResponseModel)->Unit = {}
    private val callBack = object:DiffUtil.ItemCallback<TrackResponseModel>(){
        override fun areItemsTheSame(oldItem: TrackResponseModel, newItem: TrackResponseModel): Boolean {
           return oldItem===newItem
        }

        override fun areContentsTheSame(oldItem: TrackResponseModel, newItem: TrackResponseModel): Boolean {
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
        fun bind(item:TrackResponseModel){
            binding.txtName.text = item.title

            itemView.setOnClickListener{
                navTo(item)
            }
            Glide.with(itemView)
                .load(item.album?.cover)
                .into(binding.img)
        }
    }

    fun submitList(items:List<TrackResponseModel>){
        diffUtil.submitList(items)
    }

    fun setNavFunction(nav:(TrackResponseModel)->Unit){
        this.navTo = { item->
            nav(item)
        }
    }



}