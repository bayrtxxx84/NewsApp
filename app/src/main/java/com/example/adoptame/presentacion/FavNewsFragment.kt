package com.example.adoptame.presentacion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.adoptame.databinding.FragmentFavNewsBinding


class FavNewsFragment : Fragment() {

    private lateinit var binding: FragmentFavNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavNewsBinding.inflate(inflater, container, false)

        return binding.root
    }

}