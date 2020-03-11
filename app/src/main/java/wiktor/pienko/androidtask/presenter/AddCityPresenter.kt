package wiktor.pienko.androidtask.presenter

import android.app.Activity
import android.content.Intent
import wiktor.pienko.androidtask.base.BasePresenter
import wiktor.pienko.androidtask.view.AddCityView

class AddCityPresenter(addCityView: AddCityView) : BasePresenter<AddCityView>(addCityView) {
    fun onClickedButtonOK() {
        val replyIntent= Intent()
    }
    fun onClickedButtonError(){
        val replyIntent=Intent()
    }

}
