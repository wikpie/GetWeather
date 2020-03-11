package wiktor.pienko.androidtask

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_add_city.*
import wiktor.pienko.androidtask.base.BaseActivity
import wiktor.pienko.androidtask.base.EXTRA_REPLY
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


