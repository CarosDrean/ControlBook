package xyz.drean.controlbook.fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_register.view.*
import xyz.drean.controlbook.AddAsistant

import xyz.drean.controlbook.R
import xyz.drean.controlbook.adapter.AdapterAsistant
import xyz.drean.controlbook.adapter.AdapterClass
import xyz.drean.controlbook.pojo.Asistant

/**
 * A simple [Fragment] subclass.
 */
class Register : Fragment() {

    private var db: FirebaseFirestore? = null
    private var collAsistant: CollectionReference? = null
    private var classList: RecyclerView? = null
    private var adapter: AdapterAsistant? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_register, container, false)

        v.add_auxiliar.setOnClickListener {
            val i = Intent(activity, AddAsistant::class.java)
            activity?.startActivity(i)
        }

        init(v)
        getData()

        return v
    }

    private fun init(v: View) {
        classList = v.findViewById(R.id.list_asistants)
        db = FirebaseFirestore.getInstance()
        collAsistant = db?.collection("assitants")

        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.VERTICAL
        classList?.layoutManager = llm
    }

    private fun getData() {
        val query: Query = collAsistant as Query

        val options = FirestoreRecyclerOptions.Builder<Asistant>()
            .setQuery(query, Asistant::class.java)
            .build()

        adapter = AdapterAsistant(options, activity)
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
