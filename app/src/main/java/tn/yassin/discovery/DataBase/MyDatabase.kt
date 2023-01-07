package tn.yassin.discovery.DataBase
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.curriculumvitaev2.Dao.EducationDao
import com.example.curriculumvitaev2.Dao.ExperienceDao
import com.example.curriculumvitaev2.Data.Educationinfo
import com.example.curriculumvitaev2.Data.Experienceinfo

@Database(entities = [Educationinfo::class, Experienceinfo::class], version = 1, exportSchema = false)
abstract class MyDatabase : RoomDatabase() {
    abstract fun EduDao(): EducationDao
    abstract fun ExpDao(): ExperienceDao

    companion object {
        @Volatile
        private var instance: MyDatabase? = null

        fun getDatabase(context: Context): MyDatabase {
            if (instance == null) {
                synchronized(this) {
                    instance = Room.databaseBuilder(
                        context,
                        MyDatabase::class.java,
                        "discovery-db"
                    )
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return instance!!
        }
    }
}
