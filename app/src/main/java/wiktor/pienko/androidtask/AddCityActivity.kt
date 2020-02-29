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

class AddCityActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_city)
        val textLayout=findViewById<TextInputLayout>(R.id.add_city_layout)
        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (textLayout.editText?.text.toString()=="") {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val city = textLayout.editText?.text.toString()
                replyIntent.putExtra(EXTRA_REPLY, city)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
        }
    companion object {
        const val EXTRA_REPLY = "city_name"
    }
    }


