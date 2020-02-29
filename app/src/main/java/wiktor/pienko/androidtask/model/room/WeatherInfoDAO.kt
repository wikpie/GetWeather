package wiktor.pienko.androidtask.model.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WeatherInfoDAO {
    @Query("SELECT * FROM weatherInfo ORDER BY city ASC")
    fun getAll(): LiveData<List<WeatherInfo>>

    @Insert(onConflict=OnConflictStrategy.REPLACE)
    suspend fun insert(weatherInfo: WeatherInfo)

    @Delete
    suspend fun delete(weatherInfo: WeatherInfo)

    @Query("DELETE FROM weatherInfo WHERE city=:city")
    fun deleteByCityName(city:String)

    @Query("DELETE FROM weatherinfo")
    fun deleteAll()

}