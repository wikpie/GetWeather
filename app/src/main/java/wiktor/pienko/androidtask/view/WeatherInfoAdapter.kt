package wiktor.pienko.androidtask.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import wiktor.pienko.androidtask.databinding.RecyclerviewItemBinding
import wiktor.pienko.androidtask.model.room.WeatherInfo

class WeatherInfoAdapter : RecyclerView.Adapter<WeatherInfoAdapter.WeatherInfoHolder>() {

    private var weatherInfoList = emptyList<WeatherInfo>()
    var onItemTouch: ((WeatherInfo) -> Unit)? = null
    inner class WeatherInfoHolder(val binding: RecyclerviewItemBinding ): RecyclerView.ViewHolder(binding.root) {
       init {
           itemView.setOnClickListener {
               onItemTouch?.invoke(weatherInfoList[adapterPosition])
           }
       }
       fun bind(item: WeatherInfo) {
           with(binding) {
               binding.weather=item
               Glide.with(weatherImage.context).load("http://openweathermap.org/img/w/"+item.icon+".png").into(binding.weatherImage)
               executePendingBindings()

           }
       }
   }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherInfoHolder {
        val inflater= LayoutInflater.from(parent.context)
        val binding = RecyclerviewItemBinding.inflate(inflater, parent, false)
        return WeatherInfoHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherInfoHolder, position: Int) {
        holder.bind(weatherInfoList[position])
    }

    internal fun setInfo(info: List<WeatherInfo>) {
        this.weatherInfoList = info
        notifyDataSetChanged()
    }

    override fun getItemCount() = weatherInfoList.size
}

