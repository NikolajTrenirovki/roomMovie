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
import com.example.roommovie.databinding.FragmentPanelEditFilmBinding
import com.example.roommovie.repositories.FilmsRepository
import com.example.roommovie.viewModels.FilmsFactory
import com.example.roommovie.viewModels.FilmsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PanelEditFilm : BottomSheetDialogFragment(),View.OnKeyListener, View.OnClickListener {

    private var binding: FragmentPanelEditFilmBinding? = null
    private var productRepository: FilmsRepository? = null
    private var productViewModel: FilmsViewModel? = null
    private var factory: FilmsFactory? = null
    private var idProduct:Int? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_panel_edit_film, container, false)

        idProduct = arguments?.getString("idProduct")?.toInt()
        binding?.editNameFilm?.setText(arguments?.getString("nameProduct").toString())
        binding?.editCategoryFilm?.setText(arguments?.getString("categoryProduct").toString())
        binding?.editDurationFilm?.setText(arguments?.getString("priceProduct").toString())


        val productDao = MovieDatabase.getInstance((context as FragmentActivity).application).filmsDAO
        productRepository = FilmsRepository(productDao)
        factory = FilmsFactory(productRepository!!)
        productViewModel = ViewModelProvider(this, factory!!).get(FilmsViewModel::class.java)

        binding?.editNameFilm?.setOnKeyListener(this)
        binding?.editCategoryFilm?.setOnKeyListener(this)
        binding?.editDurationFilm?.setOnKeyListener(this)

        binding?.buttonEditFilm?.setOnClickListener(this)


        return binding?.root
    }

    override fun onKey(view: View, i: Int, keyEvent: KeyEvent): Boolean {
        when (view.id) {


            R.id.editNameFilm -> {
                if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {

                    binding?.resEditNameFilm?.text = binding?.editNameFilm?.text
                    binding?.editNameFilm?.setText("")

                    return true
                }

            }

            R.id.editCategoryFilm -> {
                if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {

                    binding?.resEditCategoryFilm?.text = binding?.editCategoryFilm?.text
                    binding?.editCategoryFilm?.setText("")

                    return true
                }

            }

            R.id.editDurationFilm -> {
                if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {

                    binding?.resEditDurationFilm?.text = binding?.editDurationFilm?.text
                    binding?.editDurationFilm?.setText("")

                    return true
                }

            }
        }

        return false
    }

    override fun onClick(view: View) {
        productViewModel?.startUpdateFilm(idProduct.toString().toInt(), binding?.resEditNameFilm?.text?.toString()!!,
            binding?.resEditCategoryFilm?.text?.toString()!!, binding?.resEditDurationFilm?.text?.toString()!!)

        dismiss()

        (context as FragmentActivity).supportFragmentManager.beginTransaction().replace(R.id.content, TabCategories()).commit()
    }
}