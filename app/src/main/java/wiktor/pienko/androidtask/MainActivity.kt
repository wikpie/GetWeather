package wiktor.pienko.androidtask

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import wiktor.pienko.androidtask.model.apiConnection.Weather
import wiktor.pienko.androidtask.model.room.WeatherInfo
import wiktor.pienko.androidtask.presenter.WeatherViewModel
import wiktor.pienko.androidtask.presenter.WeatherViewModelFactory
import wiktor.pienko.androidtask.view.WeatherInfoAdapter

class MainActivity : AppCompatActivity() {

    private val activityRequestCode = 1
    private lateinit var weatherViewModel:WeatherViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val floatingActionButton=findViewById<FloatingActionButton>(R.id.fab)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview_main)
        val adapter = WeatherInfoAdapter(this)
        val viewModelFactory=WeatherViewModelFactory(application)
        weatherViewModel= ViewModelProviders.of(this, viewModelFactory).get(WeatherViewModel::class.java)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        floatingActionButton.setOnClickListener {
            val intent = Intent(this@MainActivity, AddCityActivity::class.java)
            startActivityForResult(intent, activityRequestCode )
        }
        weatherViewModel.allInfo.observe(this, Observer { info ->
            info?.let { adapter.setInfo(it) }
        })

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == activityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(AddCityActivity.EXTRA_REPLY)?.let {
                val city = it
                weatherViewModel.insert(addCity(city))
                Log.d("city",city)
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.error_adding,
                Toast.LENGTH_LONG).show()
        }

    }
    private fun addCity(city: String): WeatherInfo {
        val weather=Weather()
        return weather.addCity(city,this)
    }
}
