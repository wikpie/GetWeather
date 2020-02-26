package wiktor.pienko.androidtask.model.apiConnection

import android.content.Context
import android.util.Log
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.adapter.rxjava2.Result.response
import wiktor.pienko.androidtask.model.room.WeatherInfo


class Weather {
    private val API="41c29c7f2b18515e7134c5a03817df37"
    private val weatherInterface by lazy {
        WeatherInterface.create()
    }
    var disposable: Disposable? = null


    fun addCity(city: String, context:Context) :WeatherInfo{
        var weatherInfo=WeatherInfo("","",0.0,"")
        var list:List<WeatherDataModel>
            disposable =
                weatherInterface.getWeather(city, "metric", API)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { result ->Log.d("size", result.size.toString())
                            weatherInfo=WeatherInfo(result[0].name,result[0].weather.main,result[0].main.temp,result[0].weather.icon) },
                        { error -> Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
                        Log.d("size", error.message)}
                    )

        return weatherInfo
        }
}