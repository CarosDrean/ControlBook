package xyz.drean.controlbook.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_home.view.*

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
            val transaction = fragmentManager?.beginTransaction()
            transaction?.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                ?.replace(R.id.nav_host_fragment, ClassRom())?.commit()
        }
        return v
    }


}
