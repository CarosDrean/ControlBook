package xyz.drean.controlbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add_student.*
import xyz.drean.controlbook.abstraction.DataBase
import xyz.drean.controlbook.pojo.Student

class AddStudent : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        val idAula = intent.getStringExtra("id")

        btn_save_student.setOnClickListener {
            saveData(idAula)
        }
    }

    private fun saveData(idAula: String) {
        val db = DataBase(this)

        if(et_name_student.text.toString() == "" ||
            et_lastname_student.text.toString() == "" ||
            et_dni_student.text.toString() == "" ){
            Toast.makeText(this, "¡Llene todos los campos!", Toast.LENGTH_SHORT).show()
        } else {
            val student = Student(
                System.currentTimeMillis().toString(),
                et_name_student.text.toString(),
                et_lastname_student.text.toString(),
                et_dni_student.text.toString(),
                idAula,
                ""
            )
            db.addItem(student, student.id!!, "students", "¡Estudiante Guardado!")
            onBackPressed()
        }
    }
}
