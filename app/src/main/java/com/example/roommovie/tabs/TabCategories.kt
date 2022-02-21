package com.example.roommovie.tabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roommovie.R
import androidx.lifecycle.Observer
import com.example.roommovie.data.MovieDatabase
import com.example.roommovie.models.CategoryModel
import com.example.roommovie.repositories.CategoryRepository
import com.example.roommovie.viewModels.CategoryFactory
import com.example.roommovie.viewModels.CategoryViewModel
import com.example.roommovie.databinding.FragmentTabCategoriesBinding

class TabCategories : Fragment()  {

    private var binding:FragmentTabCategoriesBinding? = null
    private var categoryRepository: CategoryRepository? = null
    private var categoryViewModel: CategoryViewModel? = null
    private var categoryFactory: CategoryFactory? = null
    private var categoryAdapter: CategoryAdapter? = null

override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
): View? {
    // Inflate the layout for this fragment
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tab_categories, container, false)

    val categoriesDao = MovieDatabase.getInstance((context as FragmentActivity).application).categoryDAO
    categoryRepository = CategoryRepository(categoriesDao)
    categoryFactory = CategoryFactory(categoryRepository!!)
    categoryViewModel = ViewModelProvider(this, categoryFactory!!).get(CategoryViewModel::class.java)

    initRecyclerCategories()
    displayCategories()

    binding?.deleteAllCategories?.setOnClickListener(View.OnClickListener {
        categoryViewModel?.deleteAll()
    })

    return  binding?.root
}

private fun initRecyclerCategories(){
    binding?.recyclerCategories?.layoutManager = LinearLayoutManager(context)
    categoryAdapter = CategoryAdapter({categoryModel:CategoryModel->deleteCategory(categoryModel)},
        {categoryModel: CategoryModel ->editCategory(categoryModel)})
    binding?.recyclerCategories?.adapter = categoryAdapter


}

private fun displayCategories(){
    categoryViewModel?.categories?.observe(viewLifecycleOwner, Observer {
        categoryAdapter?.setList(it)
        categoryAdapter?.notifyDataSetChanged()
    })
}


private fun deleteCategory(categoryModel:CategoryModel) {
    categoryViewModel?.delete(categoryModel)
}

private fun editCategory(categoryModel:CategoryModel) {
    val panelCategory = PanelEditCategory()
    val parameters = Bundle()
    parameters.putString("idCategory", categoryModel.id.toString())
    parameters.putString("nameCategory", categoryModel.name)
    panelCategory.arguments = parameters

    panelCategory.show((context as FragmentActivity).supportFragmentManager, "editCategory")
}
}