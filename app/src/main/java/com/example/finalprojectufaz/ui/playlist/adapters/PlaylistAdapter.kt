package com.example.finalprojectufaz.ui.playlist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.finalprojectufaz.databinding.ItemPlaylistListBinding
import com.example.finalprojectufaz.domain.mocks.MockPlaylist


class PlaylistAdapter: RecyclerView.Adapter<PlaylistAdapter.ViewHolder>() {

    private val callBack = object: DiffUtil.ItemCallback<MockPlaylist>(){
        override fun areItemsTheSame(oldItem: MockPlaylist, newItem: MockPlaylist): Boolean {
            return oldItem===newItem
        }

        override fun areContentsTheSame(oldItem: MockPlaylist, newItem: MockPlaylist): Boolean {
            return oldItem==newItem
        }
    }

    private val diffUtil = AsyncListDiffer(this,callBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemPlaylistListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return diffUtil.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(diffUtil.currentList[position])
    }

    inner class ViewHolder(private val binding: ItemPlaylistListBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item:MockPlaylist){
             binding.txtCount.text = item.count.toString()
             binding.txtTrackName.text = item.name
        }
    }

    fun submitList(playlists:List<MockPlaylist>){
        diffUtil.submitList(playlists)
    }
}