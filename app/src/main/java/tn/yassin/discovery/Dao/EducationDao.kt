package com.example.curriculumvitaev2.Dao
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.curriculumvitaev2.Data.Educationinfo

@Dao
interface EducationDao {
    @Query("SELECT * FROM Educationinfo")
    fun getAll(): List<Educationinfo>

    @Query("SELECT * FROM Educationinfo where id = :id")
    fun getById(id: Int): Educationinfo

    @Insert
    fun add(champ: Educationinfo)

    @Delete
    fun delete(champ: Educationinfo)
}