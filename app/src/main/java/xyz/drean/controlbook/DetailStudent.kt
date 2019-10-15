package xyz.drean.controlbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_detail_student.*
import xyz.drean.controlbook.adapter.AdapterObservation
import xyz.drean.controlbook.pojo.Observation

class DetailStudent : AppCompatActivity() {

    private var db: FirebaseFirestore? = null
    private var collObsercation: CollectionReference? = null
    private var obsList: RecyclerView? = null
    private var adapter: AdapterObservation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_student)

        tv_name_detail.text = intent.getStringExtra("name")
        tv_lastname_detail.text = intent.getStringExtra("lastname")

        iv_back_detail.setOnClickListener { onBackPressed() }

        init()
        getData(intent.getStringExtra("id"))
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
        //val query: Query = collObsercation?.orderBy("date", Query.Direction.DESCENDING) as Query
        val query: Query = collObsercation?.whereEqualTo("idStudent", idStudent) as Query

        val options = FirestoreRecyclerOptions.Builder<Observation>()
            .setQuery(query, Observation::class.java)
            .build()

        adapter = AdapterObservation(options, this)
        // obsList?.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
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
