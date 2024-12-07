package com.example.finalprojectufaz.data.utils

object Utils {

    fun formatSecondsToMMSS(seconds: Int): String {
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        return String.format("%d:%02d", minutes, remainingSeconds)
    }
}