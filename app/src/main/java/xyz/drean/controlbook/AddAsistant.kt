package xyz.drean.controlbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add_asistant.*
import xyz.drean.controlbook.pojo.Asistant

class AddAsistant : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_asistant)
        btn_save_aux.setOnClickListener {
            saveData()
        }
    }

    private fun saveData() {
        val db = FirebaseFirestore.getInstance()

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
            db.collection("assitants")
                .add(asistant)
                .addOnSuccessListener {
                    Toast.makeText(this, "¡Auxiliar Guardado!", Toast.LENGTH_SHORT).show()
                    onBackPressed()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "¡Ocurrio un error al guardar los datos!", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
