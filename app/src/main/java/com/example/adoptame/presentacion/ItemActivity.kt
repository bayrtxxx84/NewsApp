package com.example.adoptame.presentacion

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Notification
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.adoptame.R
import com.example.adoptame.controladores.NewsController
import com.example.adoptame.database.entidades.NewsEntity
import com.example.adoptame.databinding.ActivityItemBinding
import com.example.adoptame.logica.NewsBL
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.lang.Exception


class ItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItemBinding

    private var fav: Boolean = false
    var newsObtain: NewsEntity? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.extras?.let {
            newsObtain = Json.decodeFromString<NewsEntity>(it.getString("noticia").toString())
        }

        if (newsObtain != null) {
            loadNews(newsObtain!!)
        }

        binding.floatingActionButtonItem.setOnClickListener() {
            saveFavNews(newsObtain)
        }

        binding.floatingActionButtonItem1.setOnClickListener {
            shareNews()
            //solicitarPermisos()
            //getContacts()
            //linkNews(newsObtain!!)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun solicitarPermisos() {
        when {
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                    == PackageManager.PERMISSION_GRANTED -> {
                getContacts()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS) -> {
                Toast.makeText(
                    this,
                    "El usuario ha rechazado los permisos, por favor activelos manualmente",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else -> {
                requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), 200)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {

            200 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContacts()
                } else {
                    Toast.makeText(
                        this,
                        "Los permisos han sido negados. La aplicación se vera afectada en su funcionalidad",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


    private fun loadNews(newsEntity: NewsEntity) {
        binding.txtTitulo.text = newsEntity.title
        binding.txtAutor.text = newsEntity.author
        binding.txtDesc.text = newsEntity.desc
        Picasso.get().load(newsEntity.img).into(binding.imgNews)

        lifecycleScope.launch(Dispatchers.Main) {
            fav = withContext(Dispatchers.IO) { NewsBL().checkIsSaved(newsEntity.id) }
            if (fav) {
                binding.floatingActionButtonItem.setImageResource(R.drawable.ic_favorite_24)
            }
        }
    }

    private fun saveFavNews(news: NewsEntity?) {
        if (news != null) {
            if (!fav) {
                lifecycleScope.launch {
                    NewsController().saveFavNews(news)
                    binding.floatingActionButtonItem.setImageResource(R.drawable.ic_favorite_24)
                }
            } else {
                lifecycleScope.launch {
                    NewsController().deleteFavNews(news)
                    binding.floatingActionButtonItem.setImageResource(R.drawable.ic_favorite_border_12)
                }
            }
        }
    }


    private fun linkNews(news: NewsEntity) {
        news.url = "9786748"
        //var intent = Intent(Intent.ACTION_VIEW, Uri.parse("tel: ${news.url}"))
        //startActivity(intent)
        if (verificarPaquete()) {
            var api_w = "http://api.whatsapp.com/send?phone=${news.url}&text=Hola mundo"
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse(api_w))
            intent.setPackage("com.whatsapp")
            startActivity(intent)
        } else {
            Toast.makeText(this, "La app solicitada no esta instalada", Toast.LENGTH_SHORT).show()
        }
    }

    private fun shareNews() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Hola mundo")
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }


    private fun verificarPaquete(): Boolean {
        var flag = false
        try {
            packageManager.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES)
            Log.d("TAG", "El paquete solicitado si existe")
            flag = true
        } catch (ex: Exception) {
            Log.d("TAG", "El paquete solicitado no existe")
        } finally {
            return flag
        }
    }

    @SuppressLint("Range")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getContacts() {
        var cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Log.d(
                    "TAG",
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                )
                Log.d(
                    "TAG",
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                )
            } while (cursor.moveToNext())
        }
    }
}