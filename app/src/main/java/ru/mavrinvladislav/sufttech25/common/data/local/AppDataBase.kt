package ru.mavrinvladislav.sufttech25.common.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import ru.mavrinvladislav.sufttech25.common.data.local.converters.ListConverter
import ru.mavrinvladislav.sufttech25.common.data.local.model.BookDb

@Database(
    entities = [BookDb::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ListConverter::class)
abstract class AppDatabase : RoomDatabase() {

    companion object {

        private const val DB_NAME = "main.db"
        private var instance: AppDatabase? = null
        private val MONITOR = Any()

        fun getInstance(context: Context): AppDatabase {
            instance?.let {
                return it
            }
            synchronized(MONITOR) {
                val db = Room.databaseBuilder(
                    context = context,
                    klass = AppDatabase::class.java,
                    name = DB_NAME
                ).build()
                instance = db
                return db
            }
        }
    }

    abstract fun favouriteBooksDao(): FavouriteBooksDao

}