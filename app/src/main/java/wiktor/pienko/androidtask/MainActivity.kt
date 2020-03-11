package wiktor.pienko.androidtask

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import wiktor.pienko.androidtask.base.BaseActivity
import wiktor.pienko.androidtask.base.EXTRA_REPLY
import wiktor.pienko.androidtask.databinding.ActivityMainBinding
import wiktor.pienko.androidtask.databinding.RecyclerviewItemBinding
import wiktor.pienko.androidtask.model.apiConnection.WeatherInterface
import wiktor.pienko.androidtask.model.room.WeatherInfo
import wiktor.pienko.androidtask.presenter.MainPresenter
import wiktor.pienko.androidtask.presenter.WeatherViewModel
import wiktor.pienko.androidtask.presenter.WeatherViewModelFactory
import wiktor.pienko.androidtask.view.MainView
import wiktor.pienko.androidtask.view.WeatherInfoAdapter


class MainActivity :  BaseActivity<MainPresenter>(), MainView {

    private lateinit var binding: ActivityMainBinding
    private val weatherInfoAdapter=WeatherInfoAdapter(this)
   /* private val weatherInterface by lazy {
        WeatherInterface.create()
    }*/
    var disposable: Disposable? = null
    var weatherInfo=WeatherInfo("","",0.0,"")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.adapter=weatherInfoAdapter
        binding.layoutManager= LinearLayoutManager(this)
        binding.dividerItemDecoration= DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        presenter.allInfo.observe(this, Observer { info ->
            info?.let { weatherInfoAdapter.setInfo(it) }
        })
        fab.setOnClickListener {
            presenter.onFloatingButtonClicked()
        }
        weatherInfoAdapter.onItemTouch={weatherInfo ->
            buildDialog(weatherInfo)
        }
       /* val floatingActionButton=findViewById<FloatingActionButton>(R.id.fab)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview_main)
        val adapter = WeatherInfoAdapter(this)
        val viewModelFactory=WeatherViewModelFactory(application)
        weatherViewModel= ViewModelProviders.of(this, viewModelFactory).get(WeatherViewModel::class.java)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
       adapter.onItemTouch={ weatherInfo ->
            AlertDialog.Builder(this)
                .setTitle("Delete entry")
                .setMessage("Are you sure you want to delete this entry?")
                .setPositiveButton(android.R.string.yes
                ) { _, _ ->
                    weatherViewModel.delete(weatherInfo)
                }
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()
        }
        floatingActionButton.setOnClickListener {
            val intent = Intent(this@MainActivity, AddCityActivity::class.java)
            startActivityForResult(intent, activityRequestCode )
        }
        weatherViewModel.allInfo.observe(this, Observer { info ->
            info?.let { adapter.setInfo(it) }
        })
    */
        }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == wiktor.pienko.androidtask.base.activityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(EXTRA_REPLY)?.let {
                presenter.addCity(it)
            }
        } else {
           showError(getString(R.string.error_adding))
        }
    }

    override fun buildDialog(weatherInfo: WeatherInfo) {
        AlertDialog.Builder(getContext())
            .setTitle("Delete entry")
            .setMessage("Are you sure you want to delete this entry?")
            .setPositiveButton(android.R.string.yes
            ) { _, _ ->
                presenter.delete(weatherInfo)
            }
            .setNegativeButton(android.R.string.no, null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }


    override fun instantiatePresenter(): MainPresenter {
        return MainPresenter(this)
    }


    /*
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (requestCode == activityRequestCode && resultCode == Activity.RESULT_OK) {
        data?.getStringExtra(AddCityActivity.EXTRA_REPLY)?.let {
            val city = it
            addCity(city, this)
            Log.d("city",city)
        }
    } else {
        Toast.makeText(
            applicationContext,
            R.string.error_adding,
            Toast.LENGTH_LONG).show()
    }
}

private fun addCity(city: String, context: Context){
    disposable=weatherInterface.getWeather(city, "metric", API)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            { result ->Log.d("city_full", result.toString())
                weatherInfo=WeatherInfo(result.name,result.weather[0].main,result.main.temp,result.weather[0].icon)
                Log.d("city_weather_first", weatherInfo.toString())
                weatherViewModel.insert(weatherInfo)
                disposable?.dispose()
            },
            { error -> Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
                Log.d("city_error", error.message)})
}

private fun addCity(city: String): WeatherInfo {
    val weather=Weather()
    Log.d("city_add_city", weather.addCity(city,this).toString() )
    return weather.addCity(city,this)
}*/
}
