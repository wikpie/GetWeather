package wiktor.pienko.androidtask.model.room

import androidx.lifecycle.LiveData

class WeatherInfoRepository(private val weatherInfoDAO: WeatherInfoDAO) {

    val allInfo: LiveData<List<WeatherInfo>> = weatherInfoDAO.getAll()

    suspend fun insert(weatherInfo: WeatherInfo) {
        weatherInfoDAO.insert(weatherInfo)
    }
}