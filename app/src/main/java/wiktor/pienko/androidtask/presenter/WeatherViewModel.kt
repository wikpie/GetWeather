package wiktor.pienko.androidtask.presenter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import wiktor.pienko.androidtask.model.room.WeatherInfo
import wiktor.pienko.androidtask.model.room.WeatherInfoDatabase
import wiktor.pienko.androidtask.model.room.WeatherInfoRepository

class WeatherViewModel(application: Application) : AndroidViewModel(application) {

    // The ViewModel maintains a reference to the repository to get data.
    private val repository: WeatherInfoRepository
    // LiveData gives us updated words when they change.
    val allInfo: LiveData<List<WeatherInfo>>

    init {
        // Gets reference to WordDao from WordRoomDatabase to construct
        // the correct WordRepository.
        val weatherDao = WeatherInfoDatabase.getDatabase(application,viewModelScope).weatherInfoDao()
        repository = WeatherInfoRepository(weatherDao)
        allInfo = repository.allInfo
    }

    /**
     * The implementation of insert() in the database is completely hidden from the UI.
     * Room ensures that you're not doing any long running operations on
     * the main thread, blocking the UI, so we don't need to handle changing Dispatchers.
     * ViewModels have a coroutine scope based on their lifecycle called
     * viewModelScope which we can use here.
     */
    fun insert(weatherInfo: WeatherInfo) = viewModelScope.launch {
        repository.insert(weatherInfo)
    }
    fun delete(weatherInfo: WeatherInfo) = viewModelScope.launch {
        repository.delete(weatherInfo)
    }
}