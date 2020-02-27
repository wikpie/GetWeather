package wiktor.pienko.androidtask.model.apiConnection

object WeatherDataModel {
        data class Result(val coord:Coord, val weather: List<Weather>, val base:String, val main: Main, val weatherVisibility:Int, val wind: Wind, val clouds:Clouds, val dt:Long, val sys:Sys, val id:Int, val name:String, val cod:Int)
        data class Coord(val lon:Double, val lat:Double)
        data class Wind(val speed:Double, val deg:Int)
        data class Clouds(val all:Int)
        data class Sys(val type:Int, val id:Int, val message:Double, val country:String, val sunrise:Long, val sunset: Long)
        data class Weather(val id:Int,val main:String,val description:String ,val icon:String)
        data class Main(val temp:Double,val pressure:Int,val humidity:Int,val temp_min:Double ,val temp_max:Double)
}