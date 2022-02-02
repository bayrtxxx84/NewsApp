package com.example.adoptame.presentacion

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.adoptame.R
import com.example.adoptame.database.NewsDataBase
import com.example.adoptame.databinding.ActivityPrincipalBinding
import com.example.adoptame.utils.Adoptame

class PrincipalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPrincipalBinding
    private var lstFragments = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        changeFragment(R.id.itListar, ListarFragment())
        lstFragments.add(R.id.itListar)

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.itListar -> {
                    binding.textView.text = getString(R.string.ListarProductos)
                    changeFragment(R.id.itListar, ListarFragment())
                    true
                }
                R.id.itCarrito -> {
                    binding.textView.text = getString(R.string.CarritoProductos)
                    changeFragment(R.id.itCarrito, FavNewsFragment())
                    true
                }
                R.id.itComprar -> {
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

    }

    override fun onBackPressed() {
        super.onBackPressed();
        if (lstFragments.size > 1) {
            lstFragments.removeLast()
            binding.bottomNavigation.menu.findItem(lstFragments.last()).isChecked = true
        }
    }

    private fun changeFragment(tagToChange: Int, fragment: Fragment? = null) {
        var addStack: Boolean = false
        val ft = supportFragmentManager.beginTransaction()

        if (lstFragments.isNotEmpty()) {
            val currentFragment =
                supportFragmentManager.findFragmentByTag(lstFragments.last().toString())!!
            val toChangeFragment = supportFragmentManager.findFragmentByTag(tagToChange.toString())
            currentFragment.onPause()

            if (toChangeFragment != null) {
                if (currentFragment != toChangeFragment) {
                    addStack = true
                    ft.hide(currentFragment).show(toChangeFragment)
                    toChangeFragment.onResume()
                }
            } else {
                if (fragment != null) {
                    addStack = true
                    ft.hide(currentFragment)
                        .add(binding.FrameLayout.id, fragment, tagToChange.toString())
                }
            }
        } else {
            if (fragment != null) {
                ft.add(binding.FrameLayout.id, fragment, tagToChange.toString())
                addStack = true
            }
        }

        if (addStack) {
            ft.commit()
            ft.addToBackStack(tagToChange.toString())
            lstFragments.add(tagToChange)
        }
    }
}

