package xyz.drean.controlbook.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.item_asistant.view.*

import xyz.drean.controlbook.R
import xyz.drean.controlbook.abstraction.DataBase
import xyz.drean.controlbook.pojo.Assistant
import java.util.*

class AdapterAssistant(
    options: FirestoreRecyclerOptions<Assistant>,
    private val activity: Activity
) : FirestoreRecyclerAdapter<Assistant, AdapterAssistant.AssistantHolder>(options) {

    private val dab = DataBase(activity)

    override fun onBindViewHolder(holder: AssistantHolder, i: Int, model: Assistant) {
        holder.name.text = model.name
        holder.user.text = model.user

        holder.content.setOnLongClickListener {
            dab.alertDelete(i, this as FirestoreRecyclerAdapter<Objects, RecyclerView.ViewHolder>, "Auxiliar")
            true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssistantHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_asistant, parent, false)
        return AssistantHolder(v)
    }

    inner class AssistantHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name: TextView = itemView.findViewById(R.id.txt_name_aux)
        val user: TextView = itemView.findViewById(R.id.txt_user)
        val content: RelativeLayout = itemView.content_assist

    }
}
