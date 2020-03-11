package wiktor.pienko.androidtask.view

import android.app.AlertDialog
import android.content.Intent
import android.widget.Toast
import wiktor.pienko.androidtask.AddCityActivity
import wiktor.pienko.androidtask.base.BaseView
import wiktor.pienko.androidtask.base.activityRequestCode
import wiktor.pienko.androidtask.model.room.WeatherInfo

interface MainView : BaseView {

    fun buildDialog(weatherInfo: WeatherInfo){
        AlertDialog.Builder(getContext())
            .setTitle("Delete entry")
            .setMessage("Are you sure you want to delete this entry?")
            .setPositiveButton(android.R.string.yes
            ) { _, _ ->
            }
            .setNegativeButton(android.R.string.no, null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    fun showError(error: String){
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show()
    }

    fun startAddActivity() {
        val intent = Intent(getContext(), AddCityActivity::class.java)
        getActivity().startActivityForResult(intent, activityRequestCode)
    }
}