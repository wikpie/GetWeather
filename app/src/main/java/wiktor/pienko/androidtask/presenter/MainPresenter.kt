package wiktor.pienko.androidtask.presenter

import android.util.Log
import androidx.lifecycle.LiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import wiktor.pienko.androidtask.base.API
import wiktor.pienko.androidtask.base.BasePresenter
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

    val allInfo: LiveData<List<WeatherInfo>>
    private var parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.IO

    private val scope = CoroutineScope(coroutineContext)

    init {
        val weatherDao = WeatherInfoDatabase.getDatabase(view.getContext(), scope ).weatherInfoDao()
        repository = WeatherInfoRepository(weatherDao)
        allInfo = repository.allInfo
        this.injector.inject(this)
    }

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
                    Log.d("city_error", error.message.toString())})
    }


}