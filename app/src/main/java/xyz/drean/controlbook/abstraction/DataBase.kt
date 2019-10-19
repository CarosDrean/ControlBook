package xyz.drean.controlbook.abstraction

import android.app.Activity
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter

import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.delete_item.view.*
import xyz.drean.controlbook.R
import java.util.*

class DataBase(private val activity: Activity) {

    private var db: FirebaseFirestore? = null

    init {
        getInstance()
    }

    private fun getInstance() {
        db = FirebaseFirestore.getInstance()
    }

    fun addItem(item: Any, uid: String, collection: String, message: String) {
        db!!.collection(collection).document(uid).set(item)
        Toast.makeText(this.activity, message, Toast.LENGTH_SHORT).show()
    }

    fun deleteItem(collection: String, uid: String, message: String) {
        db!!.collection(collection).document(uid)
            .delete()
            .addOnSuccessListener { Toast.makeText(activity, message, Toast.LENGTH_SHORT).show() }
    }

    fun alertDelete(position: Int, recycler: FirestoreRecyclerAdapter<Objects, RecyclerView.ViewHolder>, title: String) {
        val dialog = BottomSheetDialog(activity)
        val inflater = activity.layoutInflater
        val v = inflater.inflate(R.layout.delete_item, null)
        v.tv_delete.text = "Eliminar $title"
        v.iv_back_del.setOnClickListener { dialog.dismiss() }
        v.delete_item.setOnClickListener{
            removeItem(position, recycler, title)
            dialog.dismiss()
        }
        dialog.setContentView(v)
        dialog.show()
    }

    private fun removeItem(position: Int, recycler: FirestoreRecyclerAdapter<Objects, RecyclerView.ViewHolder>, title: String) {
        recycler.snapshots.getSnapshot(position).reference.delete()
        Toast.makeText(activity, "¡$title Eliminado!", Toast.LENGTH_SHORT).show()
    }

    fun getDate():String {
        val calendario = GregorianCalendar()
        return formatDate (calendario.get(Calendar.DAY_OF_MONTH),
            calendario.get(Calendar.MONTH),
            calendario.get(Calendar.YEAR))
    }

    private fun formatDate(dia: Int, mes: Int, ano: Int): String{
        return (String.format("%02d", dia)
                + "/" + String.format("%02d", mes + 1)
                + "/" + ano)
    }
}
