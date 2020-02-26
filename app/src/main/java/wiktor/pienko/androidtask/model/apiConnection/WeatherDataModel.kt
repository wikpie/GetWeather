package wiktor.pienko.androidtask.model.apiConnection

object WeatherDataModel {
        data class Result(val weather: Weather, val main: Main, val name:String)
        data class Weather(val id:Int,val main:String,val description:String ,val icon:String)
        data class Main(val temp:Double,val pressure:Int,val humidity:Int,val temp_min:Double ,val temp_max:Double)
}