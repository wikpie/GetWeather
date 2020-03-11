package wiktor.pienko.androidtask.presenter

import android.app.Activity
import android.content.Intent
import wiktor.pienko.androidtask.base.BasePresenter
import wiktor.pienko.androidtask.base.EXTRA_REPLY
import wiktor.pienko.androidtask.view.AddCityView

class AddCityPresenter(addCityView: AddCityView) : BasePresenter<AddCityView>(addCityView) {
    fun onClickedButtonOK(city:String) {
        val replyIntent= Intent()
        replyIntent.putExtra(EXTRA_REPLY, city)
        view.getActivity().setResult(Activity.RESULT_OK, replyIntent)
    }
    fun onClickedButtonError(){
        val replyIntent=Intent()
        view.getActivity().setResult(Activity.RESULT_CANCELED, replyIntent)
    }

}
