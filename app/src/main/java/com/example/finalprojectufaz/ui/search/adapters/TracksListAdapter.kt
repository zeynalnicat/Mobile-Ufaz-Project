package com.example.finalprojectufaz.ui.search.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalprojectufaz.databinding.ItemTrackBinding
import com.example.finalprojectufaz.domain.ResponseModel
import com.example.finalprojectufaz.domain.album.AlbumResponseModel
import com.example.finalprojectufaz.domain.nav.AlbumNavModel
import com.example.finalprojectufaz.domain.nav.TrackNavModel
import com.example.finalprojectufaz.domain.track.TrackResponseModel

class TracksListAdapter:RecyclerView.Adapter<TracksListAdapter.ViewHolder>() {
    private var navTo : (TrackNavModel)->Unit = {}
    private var navAlbum: (AlbumNavModel) ->Unit = {}
    private val callBack = object:DiffUtil.ItemCallback<ResponseModel>(){
        override fun areItemsTheSame(oldItem: ResponseModel, newItem: ResponseModel): Boolean {
           return oldItem===newItem
        }

        override fun areContentsTheSame(oldItem: ResponseModel, newItem: ResponseModel): Boolean {
            return oldItem!=newItem
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
        fun bind(item:ResponseModel){
            if(item is TrackResponseModel){
                binding.txtName.text = item.title

                itemView.setOnClickListener{
                    val trck = TrackNavModel(id = item.id, img = item.album.cover, title = item.title, artist = item.artist?.name?:"Anonim", preview = item.preview, duration = item.duration)
                    navTo(trck)
                }
                Glide.with(itemView)
                    .load(item.album?.cover)
                    .into(binding.img)
            }
            else if (item is AlbumResponseModel){
                binding.txtName.text = item.title

                itemView.setOnClickListener {
                    val albm = AlbumNavModel(id = item.id, artist = item.artist?.name?:"", cover = item.cover, tracksUri = item.tracklist, title = item.title, duration = item.duration, artistImg = item.artist?.picture?:"")
                    navAlbum(albm)
                }

                Glide.with(itemView)
                    .load(item.cover)
                    .into(binding.img)
            }

        }
    }

    fun submitList(items:List<ResponseModel>){
        diffUtil.submitList(items)
    }

    fun setNavTrack(nav:(TrackNavModel)->Unit){
        this.navTo = { item->
            nav(item)
        }
    }

    fun setNavAlbum(nav:(AlbumNavModel)->Unit){
        this.navAlbum = { item->
            nav(item)
        }
    }



}