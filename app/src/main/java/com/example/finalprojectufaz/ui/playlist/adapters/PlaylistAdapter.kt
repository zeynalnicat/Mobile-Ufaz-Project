package com.example.finalprojectufaz.ui.playlist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.finalprojectufaz.databinding.ItemPlaylistListBinding
import com.example.finalprojectufaz.domain.playlist.PlaylistDTO


class PlaylistAdapter(private val nav: (PlaylistDTO)->Unit = {},private val handleBottomSheet: (Int)->Unit ={}): RecyclerView.Adapter<PlaylistAdapter.ViewHolder>() {

    private val selectedPlaylist = mutableListOf<Int>()
    private var count = 0

    private val callBack = object: DiffUtil.ItemCallback<PlaylistDTO>(){
        override fun areItemsTheSame(oldItem: PlaylistDTO, newItem: PlaylistDTO): Boolean {
            return oldItem===newItem
        }

        override fun areContentsTheSame(oldItem: PlaylistDTO, newItem: PlaylistDTO): Boolean {
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
        fun bind(item:PlaylistDTO){
            if(item.isBottomSheet){
                binding.txtCount.visibility = View.GONE
                binding.checkBox.visibility = View.VISIBLE
                binding.checkBox.isChecked = item.isSelected
                binding.checkBox.setOnCheckedChangeListener(null)
                binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
                    item.isSelected = isChecked
                    if(isChecked){
                        selectedPlaylist.add(item.id)
                        count+=item.total
                    }else{
                        selectedPlaylist.remove(item.id)
                        count-=item.total
                    }
                }

            }else{
                binding.txtCount.text =item.total.toString()
                itemView.setOnClickListener {
                    nav(item)
                }
                itemView.setOnLongClickListener {
                    itemView.animate()
                        .scaleX(0.8f)
                        .scaleY(0.8f)
                        .setDuration(200)
                        .withEndAction{
                            itemView.animate()
                                .scaleX(1f)
                                .scaleY(1f)
                                .setDuration(200)
                                .start()
                        }
                        .start()
                    handleBottomSheet(item.id)

                    true
                }
            }

             binding.txtTrackName.text = item.name
        }
    }

    fun submitList(playlists:List<PlaylistDTO>){
        diffUtil.submitList(playlists)
    }

    fun getSelected():Map<String,Any>{
        return mapOf("selectedList" to selectedPlaylist, "count" to count)
    }
}