package wiktor.pienko.androidtask.model.apiConnection

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherInterface {
    val API: String
        get() = "41c29c7f2b18515e7134c5a03817df37"

    //api.openweathermap.org/data/2.5/weather?q={city name}&units=metric&appid=$API
    @GET("weather")
    fun getWeather(@Query("q") city: String,
                      @Query("units") units: String,
                      @Query("appid") appid: String =API):
            Observable<List<WeatherDataModel.Result>>

    companion object {
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
    }
}