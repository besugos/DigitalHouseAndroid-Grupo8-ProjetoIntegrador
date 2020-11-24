package com.besugos.marveluniverse.home.view.character

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.besugos.marveluniverse.R
import com.besugos.marveluniverse.home.datamockup.CharacterDataBase.Companion.characters
import com.besugos.marveluniverse.home.model.CharacterModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.squareup.picasso.Picasso


class CharactersFragment : Fragment() {

    private lateinit var _adapter: CharactersAdapter
    private lateinit var _view: View
    private var _listCharacters = mutableListOf<CharacterModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_characters, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _listCharacters = characters

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerCharacter)
        val manager = LinearLayoutManager(view.context)

        _view = view

        _adapter = CharactersAdapter(requireContext(), _listCharacters)
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = manager
            adapter = _adapter
        }
    }


    fun createCharacthers(): MutableList<CharacterModel> {

        for (i in 1..10) {
            val hero = CharacterModel(
                i,
                "Hero $i",
                null,
                null,
                null,
                null,
                null,
                null,
                null
            )
            _listCharacters.add(hero)
        }
        return _listCharacters
    }




}