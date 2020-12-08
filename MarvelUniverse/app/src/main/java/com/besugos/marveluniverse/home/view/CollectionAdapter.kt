package com.besugos.marveluniverse.home.view

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.besugos.marveluniverse.character.view.CharactersFragment
import com.besugos.marveluniverse.event.view.EventsFragment
import com.besugos.marveluniverse.home.view.favorites.FavoritesFragment
import com.besugos.marveluniverse.story.view.StoriesFragment

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
                    StoriesFragment()
            }
            3 -> {
                fragment = FavoritesFragment()
            }
        }

        return fragment

    }

}
