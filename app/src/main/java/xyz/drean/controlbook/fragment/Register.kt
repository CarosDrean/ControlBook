package xyz.drean.controlbook.fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_register.view.*
import xyz.drean.controlbook.AddAsistant

import xyz.drean.controlbook.R

/**
 * A simple [Fragment] subclass.
 */
class Register : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_register, container, false)

        v.add_auxiliar.setOnClickListener {
            val i = Intent(activity, AddAsistant::class.java)
            activity?.startActivity(i)
        }

        return v
    }


}
