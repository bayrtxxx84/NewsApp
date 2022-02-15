package com.example.adoptame.presentacion

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.adoptame.R
import com.example.adoptame.controladores.UsuarioController
import com.example.adoptame.databinding.ActivityLoginBinding
import com.example.adoptame.utils.Adoptame

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnForget.setOnClickListener() {
            Toast.makeText(this, "Pantalla en construcción", Toast.LENGTH_SHORT).show()
        }

        getStringSharedPreference()

        binding.btnLogin.setOnClickListener()
        {
            val access = UsuarioController().LoginUser(
                binding.txtEmail.text.toString(),
                binding.txtPassword.text.toString()
            )
            if (access) {
                binding.emailField.error = getString(R.string.error)
                binding.passwordField.error = getString(R.string.error)
            } else {
                binding.emailField.error = null
                var intent = Intent(this, PrincipalActivity::class.java)
                startActivity(intent)
            }
        }

        binding.loginPrincipal.setOnClickListener() {
            hiddenIME(binding.root)
        }

        binding.btnForget.setOnClickListener() {
            saveSharedPreference()
        }

        binding.txtSignUp.setOnClickListener() {
            getStringSharedPreference()
        }
    }


    fun hiddenIME(view: View) {
        val imm =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun saveSharedPreference() {
        var editor = Adoptame.getShareDB().edit()
        editor.putString("name_user", binding.txtEmail.text.toString())
        editor.commit()
    }

    fun getStringSharedPreference() {
        var editor = Adoptame.getShareDB()
        var txt = editor.getString("name_user", "")
        println(txt)
        if (!txt.isNullOrBlank()) {
            editor.edit().clear().commit()
            startActivity(Intent(this, PrincipalActivity::class.java))

        }
    }
}
