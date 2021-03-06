package com.besugos.marveluniverse.home.view

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.besugos.marveluniverse.character.view.CharactersFragment
import com.besugos.marveluniverse.event.view.EventsFragment
import com.besugos.marveluniverse.favorite.view.FavoritesFragment
import com.besugos.marveluniverse.comic.view.ComicsFragment

class CollectionAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {

        var fragment: Fragment =
            CharactersFragment()

        when (position) {
            0 -> {
                fragment =
                    CharactersFragment()
            }
            1 -> {
                fragment = EventsFragment()
            }
            2 -> {
                fragment =
                    ComicsFragment()
            }
            3 -> {
                fragment =
                    FavoritesFragment()
            }
        }

        return fragment

    }

}
