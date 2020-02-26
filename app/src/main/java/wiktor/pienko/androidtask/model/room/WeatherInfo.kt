package wiktor.pienko.androidtask.model.room

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherInfo(
    @PrimaryKey @NonNull @ColumnInfo(name = "city") val city:String="",
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "temperature") val temperature: Double?,
    @ColumnInfo(name = "icon") val icon:String?
)