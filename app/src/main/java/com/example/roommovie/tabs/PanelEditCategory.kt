package com.example.roommovie.tabs

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.roommovie.R
import com.example.roommovie.data.MovieDatabase
import com.example.roommovie.databinding.FragmentPanelEditCategoryBinding
import com.example.roommovie.repositories.CategoryRepository
import com.example.roommovie.viewModels.CategoryFactory
import com.example.roommovie.viewModels.CategoryViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PanelEditCategory : BottomSheetDialogFragment(),View.OnKeyListener {

    private var binding: FragmentPanelEditCategoryBinding? = null
    private var categoryRepository: CategoryRepository? = null
    private var categoryViewModel: CategoryViewModel? = null
    private var factory: CategoryFactory? = null
    private var idCategory:Int? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_panel_edit_category, container, false)

        idCategory = arguments?.getString("idCategory")?.toInt()
        binding?.editCategory?.setText(arguments?.getString("nameCategory").toString())

        val categoriesDao = MovieDatabase.getInstance((context as FragmentActivity).application).categoryDAO
        categoryRepository = CategoryRepository(categoriesDao)
        factory = CategoryFactory(categoryRepository!!)
        categoryViewModel = ViewModelProvider(this,factory!!).get(CategoryViewModel::class.java)

        binding?.editCategory?.setOnKeyListener(this)

        return binding?.root
    }

    override fun onKey(view: View, i: Int, keyEvent: KeyEvent): Boolean {
        when (view.id) {


            R.id.editCategory -> {
                if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {

                    categoryViewModel?.startUpdateProduct(idCategory.toString().toInt(), binding?.editCategory?.text?.toString()!!)

                    binding?.editCategory?.setText("")

                    dismiss()

                    (context as FragmentActivity).supportFragmentManager.beginTransaction().replace(R.id.content, TabCategories()).commit()

                    return true
                }

            }
        }

        return false
    }

}