package com.example.finalprojectufaz.ui.album_detail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalprojectufaz.databinding.ItemAlbumTrackBinding
import com.example.finalprojectufaz.domain.mocks.MockTrack


class AlbumDetailsAdapter:RecyclerView.Adapter<AlbumDetailsAdapter.ViewHolder>() {
    private var navTo : ()->Unit = {}
    private val callBack = object: DiffUtil.ItemCallback<MockTrack>(){
        override fun areItemsTheSame(oldItem: MockTrack, newItem: MockTrack): Boolean {
            return oldItem===newItem
        }

        override fun areContentsTheSame(oldItem: MockTrack, newItem: MockTrack): Boolean {
            return oldItem==newItem
        }
    }

    private val diffUtil = AsyncListDiffer(this,callBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumDetailsAdapter.ViewHolder {
        val view = ItemAlbumTrackBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return diffUtil.currentList.size
    }

    override fun onBindViewHolder(holder: AlbumDetailsAdapter.ViewHolder, position: Int) {
        return holder.bind(diffUtil.currentList[position])
    }

    inner class ViewHolder(private val binding:ItemAlbumTrackBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(track:MockTrack){
            Glide.with(itemView)
                .load(track.img)
                .into(binding.imgTrack)

            binding.txtTrackName.text = track.name
            itemView.setOnClickListener {
                navTo()
            }
        }

    }

    fun submitList(tracks:List<MockTrack>){
        diffUtil.submitList(tracks)
    }

    fun setNavFunction(nav:()->Unit){
        this.navTo = {
            nav()
        }
    }
}