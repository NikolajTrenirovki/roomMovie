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

    suspend fun insertProduct(productModel: FilmsModel){
        filmDAO.insertFilm(productModel)
    }

    suspend fun updateFilm(productModel: FilmsModel){
        filmDAO.updateFilm(productModel)
    }

    suspend fun deleteFilm(productModel: FilmsModel) {
        filmDAO.deleteFilm(productModel)
    }

    suspend fun deleteAllFilms(){
        filmDAO.deleteAllFilms()
    }
}