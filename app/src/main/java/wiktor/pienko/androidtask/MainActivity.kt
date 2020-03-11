package wiktor.pienko.androidtask

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import wiktor.pienko.androidtask.base.BaseActivity
import wiktor.pienko.androidtask.base.EXTRA_REPLY
import wiktor.pienko.androidtask.base.activityRequestCode
import wiktor.pienko.androidtask.databinding.ActivityMainBinding
import wiktor.pienko.androidtask.model.room.WeatherInfo
import wiktor.pienko.androidtask.presenter.MainPresenter
import wiktor.pienko.androidtask.view.MainView
import wiktor.pienko.androidtask.view.WeatherInfoAdapter


class MainActivity :  BaseActivity<MainPresenter>(), MainView {

    private lateinit var binding: ActivityMainBinding
    private val weatherInfoAdapter=WeatherInfoAdapter()
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
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == activityRequestCode && resultCode == Activity.RESULT_OK) {
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
}
