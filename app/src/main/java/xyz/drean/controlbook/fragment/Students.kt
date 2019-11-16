package xyz.drean.controlbook.fragment


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_students.view.*
import xyz.drean.controlbook.AddStudent

import xyz.drean.controlbook.R
import xyz.drean.controlbook.adapter.AdapterStudent
import xyz.drean.controlbook.pojo.Student

/**
 * A simple [Fragment] subclass.
 */
class Students : BottomSheetDialogFragment() {

    private var db: FirebaseFirestore? = null
    private var coll: CollectionReference? = null
    private var list: RecyclerView? = null
    private var adapter: AdapterStudent? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_students, container, false)

        val idClassRom = arguments?.getString("id")
        val contx = arguments?.getString("contx")

        v.add_student.setOnClickListener {
            val i = Intent(activity, AddStudent::class.java)
            i.putExtra("id", idClassRom)
            activity?.startActivity(i)
        }

        if(contx == "assistance") {
            v.tv_students_title.text = "Registrar Asistencias"
        } else {
            v.tv_students_title.text = "Registrar Observacion"
        }

        v.iv_back_students.setOnClickListener { dismiss() }

        init(v)
        getData(idClassRom!!, contx!!)

        return v
    }

    private fun init(v: View) {
        list = v.findViewById(R.id.list_students)

        list!!.accessibilityLiveRegion = 3

        db = FirebaseFirestore.getInstance()
        coll = db?.collection("students")

        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.VERTICAL
        list?.layoutManager = llm
    }

    private fun getData(idClassRom: String, contx: String) {
        val query: Query = coll?.whereEqualTo("idClassRom", idClassRom)?.orderBy("lastname", Query.Direction.ASCENDING) as Query

        val options = FirestoreRecyclerOptions.Builder<Student>()
            .setQuery(query, Student::class.java)
            .build()

        adapter = AdapterStudent(options, activity!!, contx)
        list?.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        list?.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        adapter?.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter?.stopListening()
    }

}
