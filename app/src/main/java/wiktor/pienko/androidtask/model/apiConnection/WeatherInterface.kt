package wiktor.pienko.androidtask.model.apiConnection

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import wiktor.pienko.androidtask.base.API

interface WeatherInterface {

    @GET("weather")
    fun getWeather(@Query("q") city: String,
                      @Query("units") units: String,
                      @Query("appid") appid: String = API):
            Observable<WeatherDataModel.Result>

    /*companion object {
        fun create(): WeatherInterface {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create())
                .addConverterFactory(
                    GsonConverterFactory.create())
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .build()

            return retrofit.create(WeatherInterface::class.java)
        }
    }*/
}