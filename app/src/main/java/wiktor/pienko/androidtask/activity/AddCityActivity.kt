package wiktor.pienko.androidtask.activity

import android.os.Bundle
import android.widget.Button
import com.google.android.material.textfield.TextInputLayout
import wiktor.pienko.androidtask.R
import wiktor.pienko.androidtask.base.BaseActivity
import wiktor.pienko.androidtask.presenter.AddCityPresenter
import wiktor.pienko.androidtask.view.AddCityView

class AddCityActivity : BaseActivity<AddCityPresenter>(), AddCityView{

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_city)
        val textLayout=findViewById<TextInputLayout>(R.id.add_city_layout)
        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            if (textLayout.editText?.text.toString()=="") {
                presenter.onClickedButtonError()
            } else {
                presenter.onClickedButtonOK(textLayout.editText?.text.toString())
            }
            finish()
        }
        }

    override fun instantiatePresenter(): AddCityPresenter {
        return AddCityPresenter(this)
    }
}


