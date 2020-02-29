package wiktor.pienko.androidtask.model.apiConnection

import android.content.Context
import android.util.Log
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import wiktor.pienko.androidtask.model.room.WeatherInfo


class Weather {
    private val API="41c29c7f2b18515e7134c5a03817df37"
    private val weatherInterface by lazy {
        WeatherInterface.create()
    }
    var disposable: Disposable? = null
    var weatherInfo=WeatherInfo("","",0.0,"")

    fun addCity(city: String, context:Context) :WeatherInfo{
                disposable=weatherInterface.getWeather(city, "metric", API)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { result ->Log.d("city_full", result.toString())
                            weatherInfo=WeatherInfo(result.name,result.weather[0].main,result.main.temp,result.weather[0].icon)
                            Log.d("city_weather_first", weatherInfo.toString())
                        },
                        { error -> Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
                        Log.d("city_error", error.message)})
        return weatherInfo
    }

    fun clearDisposable(){
        disposable?.dispose()
    }
}