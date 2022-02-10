package com.example.adoptame.presentacion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.adoptame.R
import com.example.adoptame.databinding.FragmentPreferencesBinding


class PreferencesFragment : Fragment() {

    private lateinit var binding: FragmentPreferencesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPreferencesBinding.inflate(inflater, container, false)
        return binding.root
    }




}