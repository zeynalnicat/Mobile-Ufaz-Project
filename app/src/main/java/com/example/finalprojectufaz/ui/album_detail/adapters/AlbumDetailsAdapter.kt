package com.example.finalprojectufaz.ui.album_detail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalprojectufaz.databinding.ItemAlbumTrackBinding
import com.example.finalprojectufaz.domain.album.Data
import com.example.finalprojectufaz.domain.nav.TrackNavModel



class AlbumDetailsAdapter(private val img:String="",private val action: (TrackNavModel)->Unit = {}):RecyclerView.Adapter<AlbumDetailsAdapter.ViewHolder>() {
    private var navTo : (TrackNavModel)->Unit = {}
    private val callBack = object: DiffUtil.ItemCallback<Data>(){
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem===newItem
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
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
        fun bind(track:Data){
            val trck = TrackNavModel(id = track.id.toLong(),img=img, title = track.title,track.artist.name,track.preview,track.duration)
            if(track.img==null){
                Glide.with(itemView)
                    .load(img)
                    .into(binding.imgTrack)

                itemView.setOnClickListener {
                    navTo(trck)
                }
            }
            else{
                Glide.with(itemView)
                    .load(track.artist.picture)
                    .into(binding.imgTrack)

                itemView.setOnClickListener {
                    navTo(TrackNavModel(track.id.toLong(),track.img,track.title,track.artist.name,track.preview,track.duration))
                }
            }


            binding.btnMore.setOnClickListener {
                action(trck)
            }
            binding.txtTrackName.text = track.title

        }

    }

    fun submitList(tracks:List<Data>){
        tracks.let {
            diffUtil.submitList(it)
        }
    }

    fun setNavFunction(nav:(TrackNavModel)->Unit){
        this.navTo = { item->
            nav(item)
        }
    }
}