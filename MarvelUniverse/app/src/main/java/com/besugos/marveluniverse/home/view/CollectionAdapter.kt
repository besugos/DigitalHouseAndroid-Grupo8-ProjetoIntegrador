package com.besugos.marveluniverse.home.view

import android.os.Bundle
import android.provider.CalendarContract
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class CollectionAdapter(private val fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {

        var fragment: Fragment = CharactersFragment()

        when (position) {
            0 -> {
                fragment = CharactersFragment()
            }
            1 -> {
                fragment = StoriesFragment()
            }
            2 -> {
                fragment = EventsFragment()
            }
            3 -> {
                fragment = FavoritesFragment()
            }
        }

        return fragment

    }

}
