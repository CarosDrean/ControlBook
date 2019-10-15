package xyz.drean.controlbook.adapter

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.delete_item.view.*
import kotlinx.android.synthetic.main.item_observation.view.*

import xyz.drean.controlbook.R
import xyz.drean.controlbook.pojo.Observation

private const val VIEW_TYPE_OBS = 1
private const val VIEW_TYPE_GONE = 2

class AdapterObservation(
    options: FirestoreRecyclerOptions<Observation>,
    private val activity: Activity
) : FirestoreRecyclerAdapter<Observation, ObservationHolder>(options) {

    override fun getItemViewType(position: Int): Int {
        val obs: Observation = getItem(position)

        return if(obs.observation != null) {
            VIEW_TYPE_OBS
        } else {
            VIEW_TYPE_GONE
        }
    }

    override fun onBindViewHolder(holder: ObservationHolder, i: Int, model: Observation) {
        holder.bind(model, i)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObservationHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_observation, parent, false)
        val vg = LayoutInflater.from(parent.context).inflate(R.layout.item_empty, parent, false)
        return if(viewType == VIEW_TYPE_OBS) {
            ObservationHolderView(v)
        } else {
            ObservationHolderGone(vg)
        }
    }

    private fun removeItem(position: Int) {
        snapshots.getSnapshot(position).reference.delete()
        Toast.makeText(activity, "¡Observacion Eliminada!", Toast.LENGTH_SHORT).show()
    }

    private fun alertDelete(position: Int) {
        val dialog = BottomSheetDialog(activity)
        val inflater = activity.layoutInflater
        val v = inflater.inflate(R.layout.delete_item, null)
        val content = v.delete_item
        content.setOnClickListener{
            removeItem(position)
            dialog.dismiss()
        }
        dialog.setContentView(v)
        dialog.show()
    }

    inner class ObservationHolderView(itemView: View) : ObservationHolder(itemView) {

        private val date: TextView = itemView.findViewById(R.id.tv_date)
        private val text: TextView = itemView.findViewById(R.id.tv_text)
        private val content: RelativeLayout = itemView.content_obs_detail

        override fun bind(observation: Observation, position: Int) {
            date.text = observation.date
            text.text = observation.observation
            content.setOnLongClickListener {
                alertDelete(position)
                true
            }
        }

    }

    inner class ObservationHolderGone(itemView: View) : ObservationHolder(itemView) { }
}

open class ObservationHolder (view: View) : RecyclerView.ViewHolder(view) {
    open fun bind(observation: Observation, position: Int) {}
}
