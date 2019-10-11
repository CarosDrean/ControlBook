package xyz.drean.controlbook.fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_home.view.*
import xyz.drean.controlbook.ClassRomActivity

import xyz.drean.controlbook.R

/**
 * A simple [Fragment] subclass.
 */
class Home : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_home, container, false)

        v.card_asistencias.setOnClickListener {
            val i = Intent(activity, ClassRomActivity::class.java)
            i.putExtra("context", "assistance")
            activity?.startActivity(i)
        }

        v.card_observation.setOnClickListener {
            val i = Intent(activity, ClassRomActivity::class.java)
            i.putExtra("context", "observation")
            activity?.startActivity(i)
        }
        return v
    }


}
