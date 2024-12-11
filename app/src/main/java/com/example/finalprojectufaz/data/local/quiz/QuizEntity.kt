package com.example.finalprojectufaz.data.local.quiz

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.finalprojectufaz.data.local.playlist.PlaylistEntity


@Entity("Quiz", foreignKeys = [ForeignKey(entity = PlaylistEntity::class, parentColumns = ["id"], childColumns = ["playlistId"], onDelete = ForeignKey.CASCADE)])
data class QuizEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val playlistId:Int,
    val correctAnswers : Int?=null ,
    val wrongAnswers : Int?=null,
    val isCompleted: Boolean = false,

)