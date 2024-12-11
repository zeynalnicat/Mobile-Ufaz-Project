package com.example.finalprojectufaz.data.local.quiz

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity("QuizDetailEntity", foreignKeys = [ForeignKey(entity = QuizEntity::class, parentColumns = ["id"], childColumns = ["quizId"], onDelete = ForeignKey.CASCADE)])
data class QuizDetailEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0 ,
    val isAuthor: Boolean,
    val preview:String ,
    val answer:String,
    val quizId : Int,
    val question:String ,
)