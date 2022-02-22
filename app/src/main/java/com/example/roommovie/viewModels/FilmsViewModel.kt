package com.example.roommovie.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roommovie.models.FilmsModel
import com.example.roommovie.repositories.FilmsRepository
import kotlinx.coroutines.launch

class FilmsViewModel (private val filmRepository: FilmsRepository) : ViewModel() {

    val films = filmRepository.films

    fun getFilter (nameCategory:String, durationFilm:String):
            LiveData<List<FilmsModel>> {
        return filmRepository.getFilter(nameCategory, durationFilm)
    }

    fun startInsert(nameFilm:String, categoryFilm:String, durationFilm:String) {
        insertFilm(FilmsModel(0,nameFilm, categoryFilm, durationFilm))
    }

    fun startUpdateFilm(idProduct:Int, nameFilm:String, nameCategory:String, durationFilm:String) {
        updateFilm(FilmsModel(idProduct, nameFilm, nameCategory, durationFilm))
    }

    fun insertFilm(filmsModel: FilmsModel) = viewModelScope.launch{

        filmRepository.insertFilm(filmsModel)
    }

    fun updateFilm(filmsModel: FilmsModel) = viewModelScope.launch{

        filmRepository.updateFilm(filmsModel)
    }

    fun deleteFilm(filmsModel: FilmsModel) = viewModelScope.launch{

        filmRepository.deleteFilm(filmsModel)
    }

    fun deleteAllFilms() = viewModelScope.launch{

        filmRepository.deleteAllFilms()
    }
}