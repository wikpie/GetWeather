package wiktor.pienko.androidtask.view

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.app.ActivityCompat.startActivityForResult
import wiktor.pienko.androidtask.AddCityActivity
import wiktor.pienko.androidtask.R
import wiktor.pienko.androidtask.base.BaseView
import wiktor.pienko.androidtask.base.activityRequestCode
import wiktor.pienko.androidtask.model.room.WeatherInfo
import wiktor.pienko.androidtask.presenter.MainPresenter
import java.util.function.Function

interface MainView : BaseView {
    /**
     * Updates the previous posts by the specified ones
     * @param posts the list of posts that will replace existing ones
     */
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

    /**
     * Displays an error in the view
     * @param error the error to display in the view
     */
    fun showError(error: String){
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show()
    }

    fun startAddActivity() {
        val intent = Intent(getContext(), AddCityActivity::class.java)
        (getContext() as Activity).startActivityForResult(intent, activityRequestCode)
    }



}