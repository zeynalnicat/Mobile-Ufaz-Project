package com.example.finalprojectufaz.data.local.quiz

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface QuizDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuiz(quizEntity: QuizEntity):Long

    @Query("Select * from quiz where playlistId=:playlistId")
    suspend fun getQuiz(playlistId:Int):List<QuizEntity>

    @Query("Select Count(*) from quiz where playlistId=:playlistId")
    suspend fun count(playlistId: Int):Int

    @Query("Select Count(*) from quizdetailentity where quizId=:quizId")
    suspend fun numberOfQuestions(quizId: Int):Int

    @Query("Update Quiz set correctAnswers=:correctAnswers, wrongAnswers=:wrongAnswers, isCompleted=:isCompleted where id=:quizId")
    suspend fun modify(quizId:Int,correctAnswers:Int, wrongAnswers:Int, isCompleted:Boolean)

    @Query("Select id from quiz where playlistId=:playlistId")
    suspend fun getQuizId(playlistId: Int):Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSingleQuiz(quizDetailEntity: QuizDetailEntity):Long

    @Query("Select * from quiz")
    suspend fun fetchAll():List<QuizEntity>

    @Query("Select * from quizdetailentity where quizId=:quizId")
    suspend fun fetchQuizQuestions(quizId:Int):List<QuizDetailEntity>

}