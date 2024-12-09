package com.example.finalprojectufaz.data.local.playlist

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PlaylistEntity::class,TrackEntity::class], version = 1)
abstract class RoomDB:RoomDatabase() {

    abstract fun playlistDao():PlaylistDao

    companion object {
        private var INSTANCE:RoomDB? = null

        fun accessDB(context: Context):RoomDB?{
            if(INSTANCE==null) {
                synchronized(RoomDB::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,RoomDB::class.java,"MusicApp").build()
                }


            }
            return INSTANCE

        }
    }

}