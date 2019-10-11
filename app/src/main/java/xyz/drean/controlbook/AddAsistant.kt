package xyz.drean.controlbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add_asistant.*
import xyz.drean.controlbook.abstraction.DataBase
import xyz.drean.controlbook.pojo.Asistant

class AddAsistant : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_asistant)
        btn_save_aux.setOnClickListener {
            saveData()
        }

        iv_back_reg.setOnClickListener { onBackPressed() }
    }

    private fun saveData() {
        val db = DataBase(this)

        if(et_name_aux.text.toString() == "" ||
            et_lastname_aux.text.toString() == "" ||
            et_dni_aux.text.toString() == "" ||
            et_user_aux.text.toString() == "" ||
            et_password_aux.text.toString() == ""){
            Toast.makeText(this, "¡Llene todos los campos!", Toast.LENGTH_SHORT).show()
        } else {
            val asistant = Asistant(
                System.currentTimeMillis().toString(),
                et_name_aux.text.toString(),
                et_lastname_aux.text.toString(),
                et_dni_aux.text.toString(),
                et_user_aux.text.toString(),
                et_password_aux.text.toString()
            )

            db.addItem(asistant, asistant.id!!, "assitants", "¡Auxiliar Guardado!")
            onBackPressed()
        }
    }
}
