package xyz.drean.controlbook.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_add_parent.*
import kotlinx.android.synthetic.main.fragment_add_parent.view.*

import xyz.drean.controlbook.R
import xyz.drean.controlbook.abstraction.DataBase
import xyz.drean.controlbook.pojo.Parent

/**
 * A simple [Fragment] subclass.
 */
class AddParent : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v =inflater.inflate(R.layout.fragment_add_parent, container, false)

        v.btn_save_parent.setOnClickListener { saveData(v) }
        v.iv_back_par.setOnClickListener { dismiss() }

        return v
    }

    private fun saveData(v: View) {
        val db = DataBase(activity!!)

        if(v.et_name_par.text.toString() == "" ||
            v.et_lastname_par.text.toString() == "" ||
            v.et_dni_par.text.toString() == "" ||
            v.et_user_par.text.toString() == "" ||
            v.et_password_par.text.toString() == ""){
            Toast.makeText(activity, "¡Llene todos los campos!", Toast.LENGTH_SHORT).show()
        } else {
            val assistant = Parent(
                System.currentTimeMillis().toString(),
                v.et_name_par.text.toString(),
                v.et_lastname_par.text.toString(),
                v.et_dni_par.text.toString(),
                v.et_user_par.text.toString(),
                v.et_password_par.text.toString()
            )

            db.addItem(assistant, assistant.id!!, "parents", "¡Padre Guardado!")
            dismiss()
        }
    }


}
