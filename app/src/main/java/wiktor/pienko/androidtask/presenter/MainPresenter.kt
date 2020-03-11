package wiktor.pienko.androidtask.presenter

import android.app.Application
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import wiktor.pienko.androidtask.AddCityActivity
import wiktor.pienko.androidtask.MainActivity
import wiktor.pienko.androidtask.base.API
import wiktor.pienko.androidtask.base.BasePresenter
import wiktor.pienko.androidtask.base.activityRequestCode
import wiktor.pienko.androidtask.injection.component.PresenterInjector
import wiktor.pienko.androidtask.model.apiConnection.WeatherInterface
import wiktor.pienko.androidtask.model.room.WeatherInfo
import wiktor.pienko.androidtask.model.room.WeatherInfoDatabase
import wiktor.pienko.androidtask.model.room.WeatherInfoRepository
import wiktor.pienko.androidtask.view.MainView
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class MainPresenter(mainView: MainView) : BasePresenter<MainView>(mainView) {

    @Inject
    lateinit var weatherInterface: WeatherInterface
    private var disposable: Disposable? = null
    var repository: WeatherInfoRepository

    // LiveData gives us updated words when they change.
    val allInfo: LiveData<List<WeatherInfo>>
    private var parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.IO

    private val scope = CoroutineScope(coroutineContext)

    init {
        // Gets reference to WordDao from WordRoomDatabase to construct
        // the correct WordRepository.
        val weatherDao = WeatherInfoDatabase.getDatabase(view.getContext(), scope ).weatherInfoDao()
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
    fun insert(weatherInfo: WeatherInfo) = scope.launch {
        repository.insert(weatherInfo)
    }
    fun delete(weatherInfo: WeatherInfo) = scope.launch {
        repository.delete(weatherInfo)
    }

    fun onFloatingButtonClicked(){
        view.startAddActivity()
    }

    fun addCity(city: String) {
        disposable=weatherInterface.getWeather(city, "metric", API)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->Log.d("city_full", result.toString())
                    val weatherInfo=WeatherInfo(result.name,result.weather[0].main,result.main.temp,result.weather[0].icon)
                    Log.d("city_weather_first", weatherInfo.toString())
                    insert(weatherInfo)
                    disposable?.dispose()
                },
                { error -> view.showError(error.message.toString())
                    Log.d("city_error", error.message)})
    }


}