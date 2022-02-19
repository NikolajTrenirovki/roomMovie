package com.example.roommovie.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.roommovie.models.FilmsModel

@Dao
interface FilmsDao {

    @Insert
    suspend fun insertFilm(productModel: FilmsModel)

    @Update
    suspend fun updateFilm(productModel: FilmsModel)

    @Delete
    suspend fun deleteFilm(productModel: FilmsModel)

    @Query("DELETE FROM films_data_table")
    suspend fun deleteAllFilms()

    @Query("SELECT * FROM films_data_table")
    fun getAllFilms(): LiveData<List<FilmsModel>>

    @Query("SELECT * FROM films_data_table WHERE films_category = :nameCategory AND films_duration = :filmDuration")
    fun getFilter(nameCategory:String, filmDuration:String): LiveData<List<FilmsModel>>

    // @Query("SELECT * FROM product_data_table WHERE product_category = 'Одежда' AND product_price = '2000'")
    //fun getClothes(): LiveData<List<ProductModel>>
}