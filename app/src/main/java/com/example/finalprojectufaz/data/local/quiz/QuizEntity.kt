package com.example.finalprojectufaz.data.local.quiz

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.finalprojectufaz.data.local.playlist.PlaylistEntity


@Entity("Quiz")
data class QuizEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val name:String,
    val duration:Int,
    val numberOfQuestions:Int,
    val correctAnswers : Int?=null ,
    val wrongAnswers : Int?=null,
    val isCompleted: Boolean = false,

)