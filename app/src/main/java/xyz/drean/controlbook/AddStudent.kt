package xyz.drean.controlbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add_student.*
import xyz.drean.controlbook.pojo.Student

class AddStudent : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        val id = intent.getStringExtra("id")

        btn_save_student.setOnClickListener {
            saveData(id)
        }
    }

    private fun saveData(idAula: String) {
        val db = FirebaseFirestore.getInstance()

        if(et_name_student.text.toString() == "" ||
            et_lastname_student.text.toString() == "" ||
            et_dni_student.text.toString() == "" ){
            Toast.makeText(this, "¡Llene todos los campos!", Toast.LENGTH_SHORT).show()
        } else {
            val asistant = Student(
                System.currentTimeMillis().toString(),
                et_name_student.text.toString(),
                et_lastname_student.text.toString(),
                et_dni_student.text.toString(),
                idAula
            )
            db.collection("students")
                .add(asistant)
                .addOnSuccessListener {
                    Toast.makeText(this, "¡Estudiante Guardado!", Toast.LENGTH_SHORT).show()
                    onBackPressed()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "¡Ocurrio un error al guardar los datos!", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
