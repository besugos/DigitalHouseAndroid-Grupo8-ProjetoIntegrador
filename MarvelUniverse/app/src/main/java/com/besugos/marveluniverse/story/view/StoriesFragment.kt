package com.besugos.marveluniverse.story.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.besugos.marveluniverse.R
import com.besugos.marveluniverse.story.model.StoryModel


class StoriesFragment : Fragment() {

    private lateinit var _adapter: StoryAdapter
    private lateinit var _view: View
    private var _listStory = mutableListOf<StoryModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createStories()

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerStory)
        val manager = LinearLayoutManager(view.context)

        _view = view

        _adapter = StoryAdapter(
            requireContext(),
            _listStory
        )
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = manager
            adapter = _adapter
        }
    }

    fun createStories(): MutableList<StoryModel> {
        _listStory.clear()
        for (i in 1..10) {
            val story = StoryModel(
                i,
                "Stories $i",
                null,
                null,
                null,
                null,
                null,
                null,
                null
            )
            _listStory.add(story)
        }
        return _listStory
    }
}