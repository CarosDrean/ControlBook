package xyz.drean.controlbook.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

import xyz.drean.controlbook.DetailStudent
import xyz.drean.controlbook.R
import xyz.drean.controlbook.fragment.Parents
import xyz.drean.controlbook.pojo.Parent

class AdapterParent(
    options: FirestoreRecyclerOptions<Parent>,
    private val activity: Activity,
    private val fragm: Parents
) : FirestoreRecyclerAdapter<Parent, AdapterParent.ParentHolder>(options) {

    override fun onBindViewHolder(holder: ParentHolder, i: Int, model: Parent) {
        holder.name.text = model.name
        holder.user.text = model.lastname
        holder.content.setOnClickListener {
            (activity as DetailStudent).assignParent(model.id!!)
            fragm.dismiss()
            Toast.makeText(activity, "¡Padre asignado!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_parent, parent, false)
        return ParentHolder(v)
    }

    inner class ParentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name: TextView = itemView.findViewById(R.id.txt_name_par)
        val user: TextView = itemView.findViewById(R.id.txt_user_par)
        val content: RelativeLayout = itemView.findViewById(R.id.content_parent)

    }
}
