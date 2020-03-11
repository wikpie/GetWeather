package wiktor.pienko.androidtask.model.apiConnection

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import wiktor.pienko.androidtask.base.API

interface WeatherInterface {

    @GET("weather")
    fun getWeather(@Query("q") city: String,
                      @Query("units") units: String,
                      @Query("appid") appid: String = API):
            Observable<WeatherDataModel.Result>
}