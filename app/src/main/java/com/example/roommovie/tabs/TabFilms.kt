package com.example.roommovie.tabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roommovie.R
import com.example.roommovie.data.MovieDatabase
import com.example.roommovie.models.FilmsModel
import com.example.roommovie.repositories.FilmsRepository
import com.example.roommovie.viewModels.FilmsFactory
import com.example.roommovie.viewModels.FilmsViewModel
import com.example.roommovie.databinding.FragmentTabFilmsBinding

class TabFilms : Fragment() {
    private var binding: FragmentTabFilmsBinding? = null
    private var filmRepository: FilmsRepository? = null
    private var filmViewModel: FilmsViewModel? = null
    private var filmFactory: FilmsFactory? = null
    private var filmAdapter: FilmsAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tab_films, container, false)

        val filmDao = MovieDatabase.getInstance((context as FragmentActivity).application).filmsDAO
        filmRepository = FilmsRepository(filmDao)
        filmFactory = FilmsFactory(filmRepository!!)
        filmViewModel = ViewModelProvider(this, filmFactory!!).get(FilmsViewModel::class.java)
        initRecyclerFilms()

        val onClickListener = binding?.deleteAllProducts?.setOnClickListener(View.OnClickListener {
            filmViewModel?.deleteAllFilms()
        })

        return binding?.root
    }

    private fun initRecyclerFilms(){
        binding?.recyclerFilms?.layoutManager = LinearLayoutManager(context)
        filmAdapter = FilmsAdapter({filmModel:FilmsModel->deleteFilm(filmModel)},
            {filmModel: FilmsModel ->editFilm(filmModel)})
        binding?.recyclerFilms?.adapter = filmAdapter

        displayFilms()
    }

    private fun displayFilms(){
        filmViewModel?.films?.observe(viewLifecycleOwner, Observer {
            filmAdapter?.setList(it)
            filmAdapter?.notifyDataSetChanged()
        })
    }

    private fun deleteFilm(filmModel:FilmsModel) {
        filmViewModel?.deleteFilm(filmModel)
    }

    private fun editFIlm(filmModel:FilmsModel) {
        val panelEditFilm = FilmsEditFilm()
        val parameters = Bundle()
        parameters.putString("idFilm", filmModel.id.toString())
        parameters.putString("nameFilm", filmModel.name)
        parameters.putString("categoryFilm", filmModel.category)
        parameters.putString("priceFilm", filmModel.duration)
        panelEditFilm.arguments = parameters

        panelEditFilm.show((context as FragmentActivity).supportFragmentManager, "editFilm")
    }
}