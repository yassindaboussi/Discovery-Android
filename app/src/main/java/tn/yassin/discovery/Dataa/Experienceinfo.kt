package com.example.curriculumvitaev2.Data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Experienceinfo(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val ExpImage: Int,
    val ExpStartDate: String,
    val ExpEndDate: String,
    val ExpName: String,
    val ExpAdress: String,
    val ExpDescription: String
)

