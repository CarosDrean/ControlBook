package xyz.drean.controlbook.abstraction

import android.app.Activity
import android.widget.Toast

import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FirebaseFirestore
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
