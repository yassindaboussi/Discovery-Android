package com.example.curriculumvitaev2.Dao
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.curriculumvitaev2.Data.Experienceinfo

@Dao
interface ExperienceDao {
    @Query("SELECT * FROM Experienceinfo")
    fun getAll(): List<Experienceinfo>

    @Query("SELECT * FROM Experienceinfo where id = :id")
    fun getById(id: Int): Experienceinfo

    @Insert
    fun add(champ: Experienceinfo)

    @Delete
    fun delete(champ: Experienceinfo)

}