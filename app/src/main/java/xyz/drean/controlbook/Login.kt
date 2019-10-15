package xyz.drean.controlbook

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*
import xyz.drean.controlbook.pojo.Assistant

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_sign_in.setOnClickListener { login(et_email_field.text.toString(), et_password_field.text.toString()) }
    }

    private fun login(user: String, pass: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("assitants")
            .whereEqualTo("user", user)
            .whereEqualTo("password", pass)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    assert(task.result != null)
                    var au = 0
                    for (value in task.result!!) {
                        au = 1
                        Toast.makeText(this, "Ingresando...", Toast.LENGTH_SHORT).show()
                        val obs = value.toObject(Assistant::class.java)
                        lead(obs.id!!)
                    }
                    if (au == 0) {
                        Toast.makeText(this, "¡Credenciales invalidas!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .addOnFailureListener { Toast.makeText(this, "¡Ocurrio un error!", Toast.LENGTH_SHORT).show() }
    }

    private fun lead(idAssistant: String) {
        savePreference(idAssistant)

        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }

    private fun savePreference(idAssistant: String) {
        val sp = getSharedPreferences("config", Context.MODE_PRIVATE)
        val editor = sp?.edit()
        editor?.putString("idAssistant", idAssistant)?.apply()
    }
}
