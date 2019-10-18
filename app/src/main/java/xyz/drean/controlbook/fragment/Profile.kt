package xyz.drean.controlbook.fragment


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_profile.view.*
import xyz.drean.controlbook.Login

import xyz.drean.controlbook.R
import xyz.drean.controlbook.abstraction.DataBase
import xyz.drean.controlbook.pojo.Assistant

/**
 * A simple [Fragment] subclass.
 */
class Profile : Fragment() {

    var assistant: Assistant? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_profile, container, false)

        getDataAssistant(v)

        v.save_acces_assist.setOnClickListener { saveAccess(v) }

        v.card_logout.setOnClickListener {
            savePreference("")
            val i = Intent(activity, Login::class.java)
            activity!!.startActivity(i)
        }

        return v
    }

    private fun savePreference(idAssistant: String) {
        val sp = activity!!.getSharedPreferences("config", Context.MODE_PRIVATE)
        val editor = sp?.edit()
        editor?.putString("idAssistant", idAssistant)?.apply()
    }

    private fun getPreference(field: String): String {
        val sp = activity?.getSharedPreferences("config", Context.MODE_PRIVATE)
        val date = sp?.getString(field, "Auxiliar")
        return date!!
    }

    private fun getDataAssistant(v: View) {
        val db = FirebaseFirestore.getInstance()
        db.collection("assitants")
            .document(getPreference("idAssistant"))
            .get()
            .addOnSuccessListener {document ->
                val assist = document.toObject(Assistant::class.java)
                assistant = assist
                if(document.exists()){
                    v.name_aux.text = assist!!.name
                    v.et_user_profile.setText(assist.user)
                    v.et_password_profile.setText(assist.password)
                }
            }
    }

    private fun saveAccess(v: View) {
        val dab = DataBase(activity!!)
        v.save_acces_assist.setOnClickListener {
            assistant!!.user = v.et_user_profile.text.toString()
            assistant!!.password = v.et_password_profile.text.toString()
            dab.addItem(assistant!!, getPreference("idAssistant"), "assitants", "¡Datos Actualizados!")
        }
    }

}
