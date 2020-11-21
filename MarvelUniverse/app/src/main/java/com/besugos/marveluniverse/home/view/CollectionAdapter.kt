package com.besugos.marveluniverse.home.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class CollectionAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        val fragment = DemoFragment()
        fragment.arguments = Bundle().apply {
            putInt("ARG_OBJECT", position + 1)
        }

        return fragment

    }

}
