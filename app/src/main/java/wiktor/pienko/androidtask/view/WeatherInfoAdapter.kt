package wiktor.pienko.androidtask.view

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import wiktor.pienko.androidtask.R
import wiktor.pienko.androidtask.model.room.WeatherInfo
import kotlin.math.roundToInt

class WeatherInfoAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<WeatherInfoAdapter.WeatherInfoHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var weatherInfoList = emptyList<WeatherInfo>()

    inner class WeatherInfoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cityNameView:TextView= itemView.findViewById(R.id.city_name)
        val temperatureTextView:TextView=itemView.findViewById(R.id.temperature_text)
        val descriptionTextView:TextView=itemView.findViewById(R.id.description_text)
        val iconImageView:ImageView=itemView.findViewById(R.id.weather_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherInfoHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return WeatherInfoHolder(itemView)
    }

    override fun onBindViewHolder(holder: WeatherInfoHolder, position: Int) {
        val current = weatherInfoList[position]
        holder.cityNameView.text = current.city
        holder.descriptionTextView.text=current.description
        holder.temperatureTextView.text= (current.temperature?.roundToInt().toString())+"℃"
        Log.d("icons", current.icon)
        Glide.with(holder.iconImageView.context).load("http://openweathermap.org/img/w/"+current.icon+".png").into(holder.iconImageView)
    }

    internal fun setInfo(info: List<WeatherInfo>) {
        this.weatherInfoList = info
        notifyDataSetChanged()
    }

    override fun getItemCount() = weatherInfoList.size
}