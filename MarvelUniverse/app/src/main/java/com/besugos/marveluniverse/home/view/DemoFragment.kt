package com.besugos.marveluniverse.home.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.besugos.marveluniverse.R

private const val ARG_OBJECT = "object"

class DemoFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_demo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            val text = view.findViewById<TextView>(R.id.txt1)
            text.text = getInt(ARG_OBJECT).toString()
        }
    }
}