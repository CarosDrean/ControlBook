package xyz.drean.controlbook.fragment


import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.add_meeting.view.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import xyz.drean.controlbook.ClassRomActivity

import xyz.drean.controlbook.R
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
            val i = Intent(activity, ClassRomActivity::class.java)
            i.putExtra("context", "assistance")
            activity?.startActivity(i)
        }

        v.card_observation.setOnClickListener {
            val i = Intent(activity, ClassRomActivity::class.java)
            i.putExtra("context", "observation")
            activity?.startActivity(i)
        }

        v.card_meeting.setOnClickListener {
            alertAddMeeting()
        }

        return v
    }

    private fun alertAddMeeting() {
        val dialog: AlertDialog.Builder = AlertDialog.Builder(context!!)
        val inflater = activity!!.layoutInflater
        val v = inflater.inflate(R.layout.add_meeting, null)

        getClassRom(v.spin_classrom)

        /*val content = v.delete_item
        content.setOnClickListener{
            removeItem(position)
            dialog.dismiss()
        }*/

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
