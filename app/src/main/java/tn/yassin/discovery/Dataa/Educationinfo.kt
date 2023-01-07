package com.example.curriculumvitaev2.Data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Educationinfo(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val EduImage: Int,
    val EduStartDate: String,
    val EduEndDate: String,
    val EduName: String,
    val EduAdress: String
)


