package com.example.lab2sem4

import CharacterAPI
import CharacterAdapter
import RickAndMortyApiService
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        suspend fun parseData(page: Int): CharacterAPI {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://rickandmortyapi.com")
                .addConverterFactory(GsonConverterFactory.create()).build()

            val productAPI = retrofit.create(RickAndMortyApiService::class.java)

            val coroutine = CoroutineScope(Dispatchers.IO).async {
                val characters = productAPI.getCharacters(page)
                return@async characters
            }
            return coroutine.await()
        }

        val data: CharacterAPI

        runBlocking{ data = parseData(1)}
       val recyclerView: RecyclerView = findViewById(R.id.RecyclerID)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = CharacterAdapter(this, data.results)






    }
}

