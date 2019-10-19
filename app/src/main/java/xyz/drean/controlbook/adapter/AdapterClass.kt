package xyz.drean.controlbook.adapter

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog

import xyz.drean.controlbook.R
import xyz.drean.controlbook.abstraction.DataBase
import xyz.drean.controlbook.fragment.Students
import xyz.drean.controlbook.pojo.ClassRom
import java.util.*

class AdapterClass(
    options: FirestoreRecyclerOptions<ClassRom>,
    private val activity: Activity,
    private val contx: String
) : FirestoreRecyclerAdapter<ClassRom, AdapterClass.ClassHolder>(options) {

    private val dab: DataBase = DataBase(activity)

    override fun onBindViewHolder(holder: ClassHolder, i: Int, model: ClassRom) {
        holder.name.text = model.name
        holder.turno.text = model.turno

        holder.content.setOnClickListener { lead(model.id) }

        holder.content.setOnLongClickListener {
            dab.alertDelete(i, this as FirestoreRecyclerAdapter<Objects, RecyclerView.ViewHolder>, "Aula")
            true
        }
    }

    private fun lead(idClass: String?) {
        val args = Bundle()
        args.putString("id", idClass)
        args.putString("contx", contx)
        val students = Students()
        students.arguments = args
        students.show((activity as AppCompatActivity).supportFragmentManager, "Add Class Rom")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_classrom, parent, false)
        return ClassHolder(v)
    }


    inner class ClassHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name: TextView = itemView.findViewById(R.id.txt_name)
        val turno: TextView = itemView.findViewById(R.id.txt_turno)
        val content: RelativeLayout = itemView.findViewById(R.id.content_class)

    }
}
