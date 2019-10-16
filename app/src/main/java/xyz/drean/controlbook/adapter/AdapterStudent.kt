package xyz.drean.controlbook.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.add_observation.view.*
import kotlinx.android.synthetic.main.delete_item.view.delete_item
import kotlinx.android.synthetic.main.item_student.view.*
import kotlinx.android.synthetic.main.item_student_obs.view.*
import xyz.drean.controlbook.DetailStudent

import xyz.drean.controlbook.R
import xyz.drean.controlbook.abstraction.DataBase
import xyz.drean.controlbook.pojo.Observation
import xyz.drean.controlbook.pojo.Student

class AdapterStudent(
    options: FirestoreRecyclerOptions<Student>,
    private val activity: Activity,
    private val contx: String
) : FirestoreRecyclerAdapter<Student, StudentHolder>(options) {

    private val dab: DataBase = DataBase(activity)
    private val coll = "observations"
    private val db = FirebaseFirestore.getInstance()
    private var date: String? = null

    init {
        date = getPreference("dateAssistance")
        Toast.makeText(activity, date, Toast.LENGTH_SHORT).show()
    }

    private fun getPreference(field: String): String {
        val sp = activity.getSharedPreferences("config", Context.MODE_PRIVATE)
        val date = sp?.getString(field, dab.getDate())
        return date!!
    }

    override fun onBindViewHolder(holder: StudentHolder, i: Int, model: Student) {
        holder.bind(model, i)
    }

    private fun lead(model: Student, assistance: CheckBox) {
        if (contx == "assistance") {
            verifyAssistance(model.id, date!!, assistance)

            assistance.setOnCheckedChangeListener { buttonView, isChecked ->
                checkAssistance(
                    model.id,
                    isChecked.toString(),
                    date!!
                )
            }
        }
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

    private fun verifyAssistance(idStudent: String?, date: String, box: CheckBox) {
        assistance(idStudent, date)
            .addSnapshotListener { value, e ->
                assert(value != null)
                for (doc in value!!) {
                    val obs = doc.toObject(Observation::class.java)
                    box.isChecked = java.lang.Boolean.valueOf(obs.assistance)
                }
            }
    }

    private fun assistance(idStudent: String?, date: String): Query {
        return db.collection(coll)
            .whereEqualTo("date", date)
            .whereEqualTo("idStudent", idStudent)
    }

    private fun saveAssistance(idStudent: String?, assistance: String, date: String) {
        val obs = Observation()
        obs.id = System.currentTimeMillis().toString()
        obs.date = date
        obs.assistance = assistance
        obs.idStudent = idStudent

        assert(obs.id != null)

        dab.addItem(obs, obs.id!!, coll, "¡Asistencia Guardada!")
    }

    private fun removeItem(position: Int) {
        snapshots.getSnapshot(position).reference.delete()
        Toast.makeText(activity, "¡Alumno Eliminado!", Toast.LENGTH_SHORT).show()
    }

    private fun checkAssistance(idStudent: String?, assistance: String, date: String) {
        assistance(idStudent, date)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    assert(task.result != null)
                    var au = 0
                    for (value in task.result!!) {
                        au = 1
                        val obs = value.toObject(Observation::class.java)
                        obs.assistance = assistance
                        db.collection(coll).document(value.id).set(obs)
                    }
                    if (au == 0) {
                        saveAssistance(idStudent, assistance, date)
                    }
                }
            }
    }

    private fun alertAddObservation(idStudent: String?) {
        val dialog: AlertDialog.Builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        val v = inflater.inflate(R.layout.add_observation, null)

        val dat = DataBase(activity)

        dialog.setView(v)
        val alert = dialog.create()

        assistance(idStudent, dab.getDate())
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    assert(task.result != null)
                    for (value in task.result!!) {
                        val obs = value.toObject(Observation::class.java)
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
        assistance(idStudent, date)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    assert(task.result != null)
                    var au = 0
                    for (value in task.result!!) {
                        au = 1
                        val obs = value.toObject(Observation::class.java)
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
        val obs = Observation()
        obs.id = System.currentTimeMillis().toString()
        obs.date = date
        obs.observation = observation
        obs.idStudent = idStudent

        assert(obs.id != null)

        dab.addItem(obs, obs.id!!, coll, "¡Observacion Guardada!")
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
        private val detail: ImageView = itemView.icon_detail_student
        private val back: ImageView = itemView.iv_back_add_obs

        override fun bind(model: Student, position: Int) {
            name.text = model.name
            lastname.text = model.lastname

            content.setOnClickListener { alertAddObservation(model.id) }

            content.setOnLongClickListener {
                alertDelete(position)
                true
            }

            detail.setOnClickListener {
                val i = Intent(activity, DetailStudent::class.java)
                i.putExtra("id", model.id)
                i.putExtra("name", model.name)
                i.putExtra("lastname", model.lastname)
                activity.startActivity(i)
            }

            back.setOnClickListener { activity.onBackPressed() }
        }

    }
}

open class StudentHolder (view: View): RecyclerView.ViewHolder(view) {
    open fun bind(model: Student, position: Int) {}
}