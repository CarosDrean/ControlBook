package xyz.drean.controlbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_detail_student.*
import xyz.drean.controlbook.adapter.AdapterObservation
import xyz.drean.controlbook.fragment.AddParent
import xyz.drean.controlbook.fragment.Parents
import xyz.drean.controlbook.pojo.Observation
import xyz.drean.controlbook.pojo.Parent
import xyz.drean.controlbook.pojo.Student

class DetailStudent : AppCompatActivity() {

    private var db: FirebaseFirestore? = null
    private var collObsercation: CollectionReference? = null
    private var obsList: RecyclerView? = null
    private var adapter: AdapterObservation? = null
    private var idStudent: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_student)

        tv_name_detail.text = intent.getStringExtra("name")
        tv_lastname_detail.text = intent.getStringExtra("lastname")

        iv_back_detail.setOnClickListener { onBackPressed() }

        idStudent = intent.getStringExtra("id")

        init()
        getData(idStudent!!)

        val args = Bundle()
        args.putString("idStudent", idStudent)

        asign_parent.setOnClickListener {
            val parents = Parents()
            parents.arguments = args
            parents.show(supportFragmentManager, "Asign Parent")
        }

        parent_assignated.setOnClickListener {
            val parents = Parents()
            parents.arguments = args
            parents.show(supportFragmentManager, "Asign Parent")
        }

        if(intent.getStringExtra("idParent") == "" || intent.getStringExtra("idParent") == null) {
            card_parent.isVisible = false
            asign_parent.isVisible = true
        } else {
            getParents(intent.getStringExtra("idParent"))
        }
    }

    fun assignParent(idParent: String) {
        val student = Student(idStudent!!,
            intent.getStringExtra("name"),
            intent.getStringExtra("lastname"),
            intent.getStringExtra("dni"),
            intent.getStringExtra("idClassRom"),
            idParent)

        db?.collection("students")!!
            .document(idStudent!!)
            .set(student)

        getParents(student.idParent!!)
    }

    private fun getParents(idParent: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("parents")
            .document(idParent)
            .get()
            .addOnSuccessListener {document ->
                val parent = document.toObject(Parent::class.java)
                if(document.exists()){
                    txt_name_par.text = parent!!.name
                    txt_user_par.text = parent.lastname
                    card_parent.isVisible = true
                    asign_parent.isVisible = false
                }
            }
    }

    private fun init() {
        obsList = findViewById(R.id.list_observations)
        db = FirebaseFirestore.getInstance()
        collObsercation = db?.collection("observations")

        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        obsList?.layoutManager = llm
    }

    private fun getData(idStudent: String) {
        val query: Query = collObsercation?.whereEqualTo("idStudent", idStudent)?.orderBy("id", Query.Direction.DESCENDING) as Query

        val options = FirestoreRecyclerOptions.Builder<Observation>()
            .setQuery(query, Observation::class.java)
            .build()

        adapter = AdapterObservation(options, this)
        obsList?.adapter = adapter
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
