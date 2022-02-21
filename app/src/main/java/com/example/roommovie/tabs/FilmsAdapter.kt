package com.example.roommovie.tabs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.roommovie.R
import com.example.roommovie.models.FilmsModel
import com.example.roommovie.databinding.FilmItemBinding

class FilmsAdapter (private val deleteFilm:(FilmsModel)->Unit,
                    private val editFilm:(FilmsModel)->Unit) : RecyclerView.Adapter<FilmsAdapter.FilmHolder>() {

    private val filmsList = ArrayList<FilmsModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: FilmItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.film_item, parent, false)
        return FilmHolder(binding)
    }

    override fun getItemCount(): Int {
        return filmsList.size
    }

    override fun onBindViewHolder(holder: FilmHolder, position: Int) {
        holder.bind(filmsList[position], deleteFilm, editFilm)
    }


    fun setList(films: List<FilmsModel>) {
        filmsList.clear()
        filmsList.addAll(films)

    }


    class FilmHolder(val binding: FilmItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            filmsModel: FilmsModel,
            deleteFilm: (FilmsModel) -> Unit,
            editFilm: (FilmsModel) -> Unit

        ) {

            binding.idFilm.text = filmsModel.id.toString()
            binding.nameFilm.text = filmsModel.name
            binding.categoryFilm.text = filmsModel.category
            binding.priceFilm.text = filmsModel.duration

            binding.editFilm.setOnClickListener(View.OnClickListener {
                editFilm(filmsModel)
            })

            binding.deleteFilm.setOnClickListener(View.OnClickListener {
                deleteFilm(filmsModel)
            })

        }
    }
}