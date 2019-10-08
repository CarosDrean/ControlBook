package xyz.drean.controlbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_class_rom.*
import xyz.drean.controlbook.adapter.AdapterClass
import xyz.drean.controlbook.fragment.AddClassRom
import xyz.drean.controlbook.fragment.Students
import xyz.drean.controlbook.pojo.ClassRom

class ClassRomActivity : AppCompatActivity() {

    private var db: FirebaseFirestore? = null
    private var collClassrom: CollectionReference? = null
    private var classList: RecyclerView? = null
    private var adapter: AdapterClass? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class_rom)

        add_class_rom.setOnClickListener {
            AddClassRom().show(supportFragmentManager, "Add Class Rom")
        }

        init()
        getData()
    }

    private fun init() {
        classList = findViewById(R.id.list_classrom)
        db = FirebaseFirestore.getInstance()
        collClassrom = db?.collection("classroms")

        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        classList?.layoutManager = llm
    }

    private fun getData() {
        val query: Query = collClassrom as Query

        val options = FirestoreRecyclerOptions.Builder<ClassRom>()
            .setQuery(query, ClassRom::class.java)
            .build()

        adapter = AdapterClass(options, this)
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
