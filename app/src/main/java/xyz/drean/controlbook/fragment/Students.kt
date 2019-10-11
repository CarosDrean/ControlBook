package xyz.drean.controlbook.fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    private var collStudents: CollectionReference? = null
    private var classList: RecyclerView? = null
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

        v.iv_back_students.setOnClickListener { dismiss() }

        init(v)
        getData(idClassRom!!, contx!!)

        return v
    }

    private fun init(v: View) {
        classList = v.findViewById(R.id.list_students)
        db = FirebaseFirestore.getInstance()
        collStudents = db?.collection("students")

        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.VERTICAL
        classList?.layoutManager = llm
    }

    private fun getData(idClassRom: String, contx: String) {
        val query: Query = collStudents?.orderBy("name", Query.Direction.ASCENDING)?.whereEqualTo("idClassRom", idClassRom) as Query

        val options = FirestoreRecyclerOptions.Builder<Student>()
            .setQuery(query, Student::class.java)
            .build()

        adapter = AdapterStudent(options, activity!!, contx)
        classList?.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        classList?.adapter = adapter
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
