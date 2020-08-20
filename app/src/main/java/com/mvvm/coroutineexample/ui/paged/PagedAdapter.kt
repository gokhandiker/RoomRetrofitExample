package com.mvvm.coroutineexample.ui.paged

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mvvm.coroutineexample.R
import com.mvvm.coroutineexample.ui.CharacterModel
import kotlinx.android.synthetic.main.character_card_item.view.*

class PagedAdapter : PagingDataAdapter<CharacterModel, PagedViewHolder>(UIMODEL_COMPARATOR) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagedViewHolder {
        return PagedViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.character_card_item, parent, false)
        )
    }

    companion object {
        private val UIMODEL_COMPARATOR = object : DiffUtil.ItemCallback<CharacterModel>() {
            override fun areItemsTheSame(oldItem: CharacterModel, newItem: CharacterModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CharacterModel, newItem: CharacterModel): Boolean =
                oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: PagedViewHolder, position: Int) {
        getItem(position).let {
            holder.bind(it!!)
        }
    }
}

class PagedViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val name = view.character_card_item_name
    private val root = view.character_card_root

    private lateinit var character: CharacterModel



    fun bind(character: CharacterModel) {
        this.character = character
        name.text = character.name
    }



}