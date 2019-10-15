package xyz.drean.controlbook.fragment


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.add_meeting.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import xyz.drean.controlbook.ClassRoms
import xyz.drean.controlbook.Login

import xyz.drean.controlbook.R
import xyz.drean.controlbook.pojo.Assistant
import xyz.drean.controlbook.pojo.ClassRom

/**
 * A simple [Fragment] subclass.
 */
class Home : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_home, container, false)



        activity!!.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        v.card_asistencias.setOnClickListener {
            val i = Intent(activity, ClassRoms::class.java)
            i.putExtra("context", "assistance")
            activity?.startActivity(i)
        }

        v.card_observation.setOnClickListener {
            val i = Intent(activity, ClassRoms::class.java)
            i.putExtra("context", "observation")
            activity?.startActivity(i)
        }

        v.card_meeting.setOnClickListener {
            alertAddMeeting()
        }

        verifyLogin()
        getDataAssistant()

        return v
    }

    private fun verifyLogin() {
        if(getPreference("idAssistant") == "Auxiliar") {
            val i = Intent(activity, Login::class.java)
            startActivity(i)
        }
    }

    private fun getPreference(field: String): String {
        val sp = activity?.getSharedPreferences("config", Context.MODE_PRIVATE)
        val date = sp?.getString(field, "Auxiliar")
        return date!!
    }

    private fun getDataAssistant() {
        val db = FirebaseFirestore.getInstance()
        db.collection("assitants")
            .document(getPreference("idAssistant"))
            .get()
            .addOnSuccessListener {document ->
                val assist = document.toObject(Assistant::class.java)

                if(document.exists()){
                    name_aux.text = assist!!.name
                }
            }
    }

    private fun alertAddMeeting() {
        val dialog: AlertDialog.Builder = AlertDialog.Builder(context!!)
        val inflater = activity!!.layoutInflater
        val v = inflater.inflate(R.layout.add_meeting, null)

        getClassRom(v.spin_classrom)

        dialog.setView(v)
        dialog.create()
        dialog.show()
    }

    private fun getClassRom(spin: Spinner) {
        val db = FirebaseFirestore.getInstance()
        val values = ArrayList<String>()
        values.add("Seleccionar Aula")
        values.add("Todos")
        db.collection("classroms")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    assert(task.result != null)
                    for (value in task.result!!) {
                        val obs = value.toObject(ClassRom::class.java)
                        values.add(obs.name!!)
                    }
                    val adap = ArrayAdapter<String>(context!!, android.R.layout.simple_spinner_dropdown_item, values)
                    adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spin.adapter = adap
                }
            }
    }


}
