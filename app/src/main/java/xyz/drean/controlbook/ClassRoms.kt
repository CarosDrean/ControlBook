package xyz.drean.controlbook

import android.content.Context
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.android.synthetic.main.activity_class_rom.*
import xyz.drean.controlbook.adapter.AdapterClass
import xyz.drean.controlbook.fragment.AddClassRom
import xyz.drean.controlbook.pojo.ClassRom
import java.util.*

class ClassRoms : AppCompatActivity() {

    private var db: FirebaseFirestore? = null
    private var collClassrom: CollectionReference? = null
    private var classList: RecyclerView? = null
    private var adapter: AdapterClass? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class_rom)

        updateCalendar()

        val contx = intent.getStringExtra("context")
        if(contx != "assistance") {
            card_calendar.isVisible = false
        }

        add_class_rom.setOnClickListener {
            AddClassRom().show(supportFragmentManager, "Add Class Rom")
        }

        iv_back_class.setOnClickListener { onBackPressed() }

        init()
        getData(contx)

        calendarClick()
    }

    private fun updateCalendar(){
        calendar_class.setSelectedDate(Calendar.getInstance())
        tv_cal.text = "Fecha: Hoy"

        var date = tv_cal.text.toString().substring(7)
        if(date == "Hoy") {
            date = getDate(CalendarDay.today())
        }
        savePreference(date)
    }

    private fun calendarClick() {
        calendar_class.setOnDateChangedListener { w, date, s ->
            val dateSelect = getDate(date)
            tv_cal.text = "Fecha: $dateSelect"
            savePreference(dateSelect)
        }
    }

    private fun getDate(date: CalendarDay): String {
        return (String.format("%02d",date.day)
                + "/" + String.format("%02d",date.month + 1)
                + "/" + date.year)
    }

    private fun init() {
        classList = findViewById(R.id.list_classrom)
        db = FirebaseFirestore.getInstance()
        collClassrom = db?.collection("classroms")

        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        classList?.layoutManager = llm
    }

    private fun getData(contx: String) {
        val query: Query = collClassrom?.orderBy("name", Query.Direction.ASCENDING) as Query

        val options = FirestoreRecyclerOptions.Builder<ClassRom>()
            .setQuery(query, ClassRom::class.java)
            .build()

        adapter = AdapterClass(options, this, contx)
        classList?.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        classList?.adapter = adapter
    }

    private fun savePreference(value: String) {
        val sp = getSharedPreferences("config", Context.MODE_PRIVATE)
        val editor = sp?.edit()
        editor?.putString("dateAssistance", value)?.apply()
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
