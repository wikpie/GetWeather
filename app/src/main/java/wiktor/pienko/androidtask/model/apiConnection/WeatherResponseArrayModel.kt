package wiktor.pienko.androidtask.model.apiConnection

object WeatherResponseArrayModel {
    data class arrayResponse(val list:List<WeatherDataModel>)
}