package com.example.finalprojectufaz.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.finalprojectufaz.data.local.playlist.PlaylistDao
import com.example.finalprojectufaz.data.local.playlist.PlaylistEntity
import com.example.finalprojectufaz.data.local.playlist.TrackEntity
import com.example.finalprojectufaz.data.local.quiz.QuizDao
import com.example.finalprojectufaz.data.local.quiz.QuizDetailEntity
import com.example.finalprojectufaz.data.local.quiz.QuizEntity

@Database(entities = [PlaylistEntity::class, TrackEntity::class , QuizEntity::class,QuizDetailEntity::class], version = 3)
abstract class RoomDB:RoomDatabase() {

    abstract fun playlistDao(): PlaylistDao
    abstract fun quizDao():QuizDao

    companion object {
        private var INSTANCE: RoomDB? = null

        fun accessDB(context: Context): RoomDB?{
            if(INSTANCE ==null) {
                synchronized(RoomDB::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, RoomDB::class.java,"MusicApp").build()
                }


            }
            return INSTANCE

        }
    }

}