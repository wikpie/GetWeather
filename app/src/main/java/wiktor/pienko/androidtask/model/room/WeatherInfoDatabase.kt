package wiktor.pienko.androidtask.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [WeatherInfo::class], version = 1, exportSchema = false)
abstract class WeatherInfoDatabase : RoomDatabase() {
    abstract fun weatherInfoDao(): WeatherInfoDAO

    private class WeatherDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.weatherInfoDao())
                }
            }
        }

        suspend fun populateDatabase(weatherInfoDAO: WeatherInfoDAO) {
            // Add sample words.
            var weatherInfo = WeatherInfo("Hello","jo",32.0, "ss")
            weatherInfoDAO.insert(weatherInfo)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: WeatherInfoDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): WeatherInfoDatabase {
            val tempInstance =
                INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WeatherInfoDatabase::class.java,
                    "word_database"
                )
                    .addCallback(WeatherDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}