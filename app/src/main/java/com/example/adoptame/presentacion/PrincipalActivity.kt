package com.example.adoptame.presentacion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.adoptame.R
import com.example.adoptame.databinding.ActivityPrincipalBinding

class PrincipalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPrincipalBinding
    private var lstFragments = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadFragment(ListarFragment())
        lstFragments.add(R.id.itListar)

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.itCarrito -> {
                    binding.textView.text = getString(R.string.CarritoProductos)
                    loadFragment(FavNewsFragment())
                    lstFragments.add(R.id.itCarrito)
                    true
                }
                R.id.itComprar -> {
                    lstFragments.add(R.id.itComprar)
                    true
                }
                R.id.itListar -> {
                    binding.textView.text = getString(R.string.ListarProductos)
                    loadFragment(ListarFragment())
                    lstFragments.add(R.id.itListar)
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(binding.FrameLayout.id, fragment)
            addToBackStack(null)
        }.commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (lstFragments.isNotEmpty()) {
            lstFragments.removeLast()
            binding.bottomNavigation.menu.findItem(lstFragments.last()).setChecked(true)
        }
    }

    fun tips() {

    }

}