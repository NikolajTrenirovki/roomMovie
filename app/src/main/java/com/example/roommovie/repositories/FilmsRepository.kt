package com.example.roommovie.repositories

import androidx.lifecycle.LiveData
import com.example.roommovie.data.FilmsDao
import com.example.roommovie.models.FilmsModel

class FilmsRepository (private val filmDAO: FilmsDao) {

    val films = filmDAO.getAllFilms()

    fun getFilter (nameCategory:String, durationFilm:String):
            LiveData<List<FilmsModel>> {
        return filmDAO.getFilter(nameCategory, durationFilm)
    }

    suspend fun insertFilm(filmModel: FilmsModel){
        filmDAO.insertFilm(filmModel)
    }

    suspend fun updateFilm(filmModel: FilmsModel){
        filmDAO.updateFilm(filmModel)
    }

    suspend fun deleteFilm(filmModel: FilmsModel) {
        filmDAO.deleteFilm(filmModel)
    }

    suspend fun deleteAllFilms(){
        filmDAO.deleteAllFilms()
    }
}