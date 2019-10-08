package xyz.drean.controlbook.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_add_class_rom.view.*

import xyz.drean.controlbook.R
import xyz.drean.controlbook.pojo.ClassRom

/**
 * A simple [Fragment] subclass.
 */
class AddClassRom : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_add_class_rom, container, false)

        v.btn_save_class.setOnClickListener {
            saveData(v)
        }

        return v
    }

    private fun saveData(v: View) {
        val db = FirebaseFirestore.getInstance()

        if(v.et_name_class.text.toString() == "" ||
            v.et_section.text.toString() == "" ||
            v.et_grado.text.toString() == "" ||
            v.et_turno.text.toString() == ""){
            Toast.makeText(context, "¡Llene todos los campos!", Toast.LENGTH_SHORT).show()
        } else {
            val classRom = ClassRom(
                System.currentTimeMillis().toString(),
                v.et_name_class.text.toString(),
                v.et_section.text.toString(),
                v.et_grado.text.toString(),
                v.et_turno.text.toString()
            )

            db.collection("classroms")
                .add(classRom)
                .addOnSuccessListener {
                    Toast.makeText(context, "¡Aula Guardada!", Toast.LENGTH_SHORT).show()
                    dismiss()
                }
                .addOnFailureListener {
                    Toast.makeText(context, "¡Ocurrio un error al guardar los datos!", Toast.LENGTH_SHORT).show()
                }
        }

    }


}
