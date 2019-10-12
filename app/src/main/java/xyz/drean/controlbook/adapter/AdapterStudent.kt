package xyz.drean.controlbook.adapter

import android.app.Activity
import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.add_observation.view.*
import kotlinx.android.synthetic.main.delete_item.view.*
import kotlinx.android.synthetic.main.delete_item.view.delete_item
import kotlinx.android.synthetic.main.item_student.view.*
import kotlinx.android.synthetic.main.item_student_obs.view.*

import xyz.drean.controlbook.R
import xyz.drean.controlbook.abstraction.DataBase
import xyz.drean.controlbook.adapter.AdapterStudent.StudentHolderAsist
import xyz.drean.controlbook.pojo.Obervation
import xyz.drean.controlbook.pojo.Student

class AdapterStudent(
    options: FirestoreRecyclerOptions<Student>,
    private val activity: Activity,
    private val contx: String
) : FirestoreRecyclerAdapter<Student, StudentHolder>(options) {

    private val db: DataBase = DataBase(activity)
    private val coll = "observations"

    override fun onBindViewHolder(holder: StudentHolder, i: Int, model: Student) {
        holder.bind(model, i)
    }

    private fun lead(model: Student, assistance: CheckBox) {
        if (contx == "assistance") {
            verifyAssistance(model.id, db.getDate(), assistance)

            assistance.setOnCheckedChangeListener { buttonView, isChecked ->
                checkAssistance(
                    model.id,
                    isChecked.toString(),
                    db.getDate()
                )
            }
        }
    }

    private fun verifyAssistance(idStudent: String?, date: String, box: CheckBox) {
        val dab = FirebaseFirestore.getInstance()
        dab.collection(coll)
            .whereEqualTo("date", date)
            .whereEqualTo("idStudent", idStudent)
            .addSnapshotListener { value, e ->
                assert(value != null)
                for (doc in value!!) {
                    val obs = doc.toObject(Obervation::class.java)
                    box.isChecked = java.lang.Boolean.valueOf(obs.assistance)
                }
            }
    }

    private fun checkAssistance(idStudent: String?, assistance: String, date: String) {
        val db = FirebaseFirestore.getInstance()

        db.collection(coll)
            .whereEqualTo("date", date)
            .whereEqualTo("idStudent", idStudent)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    assert(task.result != null)
                    var au = 0
                    for (value in task.result!!) {
                        au = 1
                        val obs = value.toObject(Obervation::class.java)
                        obs.assistance = assistance
                        db.collection(coll).document(value.id).set(obs)
                    }
                    if (au == 0) {
                        saveAssistance(idStudent, assistance, date)
                    }
                }
            }


    }

    private fun saveAssistance(idStudent: String?, assistance: String, date: String) {
        val obs = Obervation()
        obs.id = System.currentTimeMillis().toString()
        obs.date = date
        obs.assistance = assistance
        obs.idStudent = idStudent

        assert(obs.id != null)

        db.addItem(obs, obs.id!!, coll, "¡Asistencia Guardada!")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentHolder {
        val va = LayoutInflater.from(parent.context).inflate(R.layout.item_student, parent, false)
        val vo = LayoutInflater.from(parent.context).inflate(R.layout.item_student_obs, parent, false)
        return if (contx == "assistance") {
            StudentHolderAsist(va)
        } else {
            StudentHolderObs(vo)
        }
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

    private fun removeItem(position: Int) {
        snapshots.getSnapshot(position).reference.delete()
        Toast.makeText(activity, "¡Alumno Eliminado!", Toast.LENGTH_SHORT).show()
    }

    private fun alertAddObservation(idStudent: String?) {
        val dialog: AlertDialog.Builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        val v = inflater.inflate(R.layout.add_observation, null)

        val dat = DataBase(activity)

        dialog.setView(v)
        val alert = dialog.create()

        val db = FirebaseFirestore.getInstance()

        db.collection(coll)
            .whereEqualTo("date", dat.getDate())
            .whereEqualTo("idStudent", idStudent)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    assert(task.result != null)
                    for (value in task.result!!) {
                        val obs = value.toObject(Obervation::class.java)
                        v.et_add_obs.setText(obs.observation)
                    }
                }
            }

        v.save_obs.setOnClickListener {
            checkObservation(idStudent, v.et_add_obs.text.toString(), dat.getDate())
            alert.dismiss()
        }

        alert.show()
    }

    private fun checkObservation(idStudent: String?, observation: String, date: String) {
        val db = FirebaseFirestore.getInstance()

        db.collection(coll)
            .whereEqualTo("date", date)
            .whereEqualTo("idStudent", idStudent)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    assert(task.result != null)
                    var au = 0
                    for (value in task.result!!) {
                        au = 1
                        val obs = value.toObject(Obervation::class.java)
                        obs.observation = observation
                        db.collection(coll).document(value.id).set(obs)
                    }
                    if (au == 0) {
                        saveObservation(idStudent, observation, date)
                    }
                }
            }


    }

    private fun saveObservation(idStudent: String?, observation: String, date: String) {
        val obs = Obervation()
        obs.id = System.currentTimeMillis().toString()
        obs.date = date
        obs.observation = observation
        obs.idStudent = idStudent

        assert(obs.id != null)

        db.addItem(obs, obs.id!!, coll, "¡Observacion Guardada!")
    }

    inner class StudentHolderAsist(itemView: View) : StudentHolder(itemView) {

        private var name: TextView = itemView.findViewById(R.id.txt_name_student)
        private val lastname: TextView = itemView.findViewById(R.id.txt_lastname_student)
        private val assistance: CheckBox = itemView.findViewById(R.id.check_student)
        private val content: RelativeLayout = itemView.content_student_asist

        override fun bind(model: Student, position: Int) {
            name.text = model.name
            lastname.text = model.lastname

            content.setOnLongClickListener {
                alertDelete(position)
                true
            }

            lead(model, assistance)
        }

    }

    inner class StudentHolderObs(itemView: View) : StudentHolder(itemView) {

        private val name: TextView = itemView.findViewById(R.id.txt_name_student_obs)
        private val lastname: TextView = itemView.findViewById(R.id.txt_lastname_student_obs)
        private val content: RelativeLayout = itemView.content_student_obs

        override fun bind(model: Student, position: Int) {
            name.text = model.name
            lastname.text = model.lastname

            content.setOnClickListener { alertAddObservation(model.id) }

            content.setOnLongClickListener {
                alertDelete(position)
                true
            }
        }

    }
}

open class StudentHolder (view: View): RecyclerView.ViewHolder(view) {
    open fun bind(model: Student, position: Int) {}
}