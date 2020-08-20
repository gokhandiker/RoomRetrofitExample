package com.mvvm.coroutineexample.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mvvm.coroutineexample.R
import com.mvvm.coroutineexample.ui.CharacterModel
import kotlinx.android.synthetic.main.character_card_item.view.*

class CharactersAdapter(private val listener: CharacterItemListener) :
    RecyclerView.Adapter<CharacterViewHolder>() {

    interface CharacterItemListener {
        fun onClickedCharacter(characterId: Long)
    }

    private val items = ArrayList<CharacterModel>()

    fun setItems(items: ArrayList<CharacterModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.character_card_item, parent, false),
            listener
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(items[position])
    }

}

class CharacterViewHolder(view: View,private val listener: CharactersAdapter.CharacterItemListener) : RecyclerView.ViewHolder(view), View.OnClickListener {

    private val name = view.character_card_item_name
    private val root = view.character_card_root

    private lateinit var character: CharacterModel

    init {
    root.setOnClickListener(this)
    }

    fun bind(character: CharacterModel) {
        this.character = character
        name.text = character.name
    }

    override fun onClick(p0: View?) {
        listener.onClickedCharacter(characterId = character.id)
    }

}
