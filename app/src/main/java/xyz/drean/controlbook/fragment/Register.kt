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
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_register.view.*
import xyz.drean.controlbook.AddAsistant

import xyz.drean.controlbook.R
import xyz.drean.controlbook.adapter.AdapterAssistant
import xyz.drean.controlbook.adapter.AdapterMeeting
import xyz.drean.controlbook.pojo.Assistant
import xyz.drean.controlbook.pojo.Meeting

/**
 * A simple [Fragment] subclass.
 */
class Register : Fragment() {

    private var db: FirebaseFirestore? = null
    private var collAsistant: CollectionReference? = null
    private var classList: RecyclerView? = null
    private var adapter: AdapterAssistant? = null

    private var collM: CollectionReference? = null
    private var listM: RecyclerView? = null
    private var adapterM: AdapterMeeting? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_register, container, false)

        v.add_auxiliar.setOnClickListener {
            val i = Intent(activity, AddAsistant::class.java)
            activity?.startActivity(i)
        }

        v.iv_back_aux.setOnClickListener { activity?.onBackPressed() }

        init(v)
        getData()

        initM(v)
        getDataM()

        return v
    }

    private fun initM(v: View) {
        listM = v.findViewById(R.id.list_meetings)
        db = FirebaseFirestore.getInstance()
        collM = db?.collection("meetings")

        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.VERTICAL
        listM?.layoutManager = llm
    }

    private fun getDataM() {
        val query: Query = collM as Query

        val options = FirestoreRecyclerOptions.Builder<Meeting>()
            .setQuery(query, Meeting::class.java)
            .build()

        adapterM = AdapterMeeting(options, activity!!)
        listM?.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        listM?.adapter = adapterM
    }

    private fun init(v: View) {
        classList = v.findViewById(R.id.list_asistants)
        db = FirebaseFirestore.getInstance()
        collAsistant = db?.collection("assitants")

        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.VERTICAL
        classList?.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        classList?.layoutManager = llm
    }

    private fun getData() {
        val query: Query = collAsistant as Query

        val options = FirestoreRecyclerOptions.Builder<Assistant>()
            .setQuery(query, Assistant::class.java)
            .build()

        adapter = AdapterAssistant(options, activity!!)
        classList?.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        adapter?.startListening()
        adapterM?.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter?.stopListening()
        adapterM?.stopListening()
    }


}
