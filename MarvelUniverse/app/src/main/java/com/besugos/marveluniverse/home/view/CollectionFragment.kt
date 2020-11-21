package com.besugos.marveluniverse.home.view

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.viewpager2.widget.ViewPager2
import com.besugos.marveluniverse.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class CollectionFragment : Fragment() {

    private lateinit var _demoCollectionAdapter: CollectionAdapter
    private lateinit var _viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_collection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _demoCollectionAdapter = CollectionAdapter(this)
        _viewPager = view.findViewById(R.id.pager)
        _viewPager.adapter = _demoCollectionAdapter

        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)

        TabLayoutMediator(tabLayout, _viewPager) {tab, position ->
            when (position) {
                0 -> {
                    tab.icon = resources.getDrawable(R.drawable.ic_baseline_person_24)
                    tab.text = "Characters"
                }
                1 -> {
                    tab.icon = resources.getDrawable(R.drawable.ic_baseline_menu_book_24)
                    tab.text = "Stories"
                }
                2 -> {
                    tab.icon = resources.getDrawable(R.drawable.ic_baseline_event_24)
                    tab.text = "Events"
                }
                3 -> {
                    tab.icon = resources.getDrawable(R.drawable.ic_baseline_favorite_24)
                    tab.text = "Favorites"
                }
            }

        }.attach()


    }
}