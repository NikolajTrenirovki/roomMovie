package com.example.roommovie.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roommovie.models.CategoryModel
import com.example.roommovie.repositories.CategoryRepository
import kotlinx.coroutines.launch

class CategoryViewModel (private val categoriyRepository: CategoryRepository) : ViewModel() {

    val categories = categoriyRepository.categories

    fun startInsert(nameCategories: String) {
        insert(CategoryModel(0, nameCategories))
    }

    fun startUpdateProduct(idCategories: Int, nameCategories: String) {
        updateProduct(CategoryModel(idCategories, nameCategories))
    }

    fun insert(categoryModel: CategoryModel) = viewModelScope.launch {

        categoriyRepository.insertCategory(categoryModel)
    }

    fun updateProduct(categoryModel: CategoryModel) = viewModelScope.launch {

        categoriyRepository.updateCategory(categoryModel)
    }

    fun delete(categoryModel: CategoryModel) = viewModelScope.launch {

        categoriyRepository.deleteCategory(categoryModel)
    }

    fun deleteAll() = viewModelScope.launch {

        categoriyRepository.deleteAllCategories()
    }
}