package xyz.drean.controlbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_class_rom.*
import xyz.drean.controlbook.fragment.AddClassRom
import xyz.drean.controlbook.fragment.Students

class ClassRomActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class_rom)

        aula1.setOnClickListener {
            Students().show(supportFragmentManager, "Students")
        }

        add_class_rom.setOnClickListener {
            AddClassRom().show(supportFragmentManager, "Add Class Rom")
        }
    }
}
