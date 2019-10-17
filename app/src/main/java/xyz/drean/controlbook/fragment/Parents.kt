package xyz.drean.controlbook.fragment


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
import kotlinx.android.synthetic.main.fragment_parents.view.*

import xyz.drean.controlbook.R
import xyz.drean.controlbook.adapter.AdapterParent
import xyz.drean.controlbook.pojo.Parent

/**
 * A simple [Fragment] subclass.
 */
class Parents : BottomSheetDialogFragment() {

    private var db: FirebaseFirestore? = null
    private var collParents: CollectionReference? = null
    private var parentList: RecyclerView? = null
    private var adapter: AdapterParent? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_parents, container, false)

        v.iv_back_parents.setOnClickListener { dismiss() }
        v.add_parent.setOnClickListener { AddParent().show(activity!!.supportFragmentManager, "Add Parent") }
        init(v)
        getData()

        return v
    }

    private fun init(v: View) {
        parentList = v.findViewById(R.id.list_parents)
        db = FirebaseFirestore.getInstance()
        collParents = db?.collection("parents")

        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.VERTICAL
        parentList?.layoutManager = llm
    }

    private fun getData() {
        val query: Query = collParents?.orderBy("name", Query.Direction.DESCENDING) as Query

        val options = FirestoreRecyclerOptions.Builder<Parent>()
            .setQuery(query, Parent::class.java)
            .build()

        adapter = AdapterParent(options, activity!!, this)
        parentList?.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        parentList?.adapter = adapter
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
