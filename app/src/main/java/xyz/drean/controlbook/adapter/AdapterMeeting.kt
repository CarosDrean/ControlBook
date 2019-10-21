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
import kotlinx.android.synthetic.main.item_observation.view.*
import xyz.drean.controlbook.R
import xyz.drean.controlbook.abstraction.DataBase
import xyz.drean.controlbook.pojo.Meeting
import java.util.*

class AdapterMeeting(
    options: FirestoreRecyclerOptions<Meeting>,
    private val activity: Activity
) : FirestoreRecyclerAdapter<Meeting, AdapterMeeting.MeetingHolder>(options) {

    private val dab = DataBase(activity)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeetingHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_observation, parent, false)
        return MeetingHolder(v)
    }

    override fun onBindViewHolder(holder: MeetingHolder, position: Int, model: Meeting) {

        holder.date.text = model.date
        holder.text.text = model.text

        holder.content.setOnLongClickListener {
            dab.alertDelete(position, this as FirestoreRecyclerAdapter<Objects, RecyclerView.ViewHolder>, "Reunión")
            true
        }
    }

    inner class MeetingHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val date: TextView = itemView.findViewById(R.id.tv_date)
        val text: TextView = itemView.findViewById(R.id.tv_text)
        val content: RelativeLayout = itemView.content_obs_detail
    }
}