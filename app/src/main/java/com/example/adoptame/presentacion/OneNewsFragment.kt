package com.example.adoptame.presentacion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.adoptame.controladores.NoticiasController
import com.example.adoptame.databinding.FragmentOneNewsBinding
import com.squareup.picasso.Picasso


class OneNewsFragment : Fragment() {

    private lateinit var binding : FragmentOneNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOneNewsBinding.inflate(inflater, container, false)

        binding.listParentLayout.setOnClickListener() {
            val news = NoticiasController().getOneNews()
            binding.txtAutor.text = news.author
            binding.txtDesc.text = news.desc
            binding.txtTitulo.text = news.title

            Picasso.get().load(news.img).into(binding.imgNews);
        }

        return binding.root
    }

}