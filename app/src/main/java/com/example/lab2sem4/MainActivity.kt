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
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.w3c.dom.CharacterData
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
        val recyclerView: RecyclerView = findViewById(R.id.RecyclerID)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val characterModel = ViewModelProvider(this).get(CharacterData::class.java)
        characterModel.coroutine.start()
        characterModel.data.observe(this, Observer<CharacterAPI>{
            value: CharacterAPI -> recyclerView.adapter = CharacterAdapter(this, value.results) //обсервер, который наблюдает за изменением data
        })

    }
    class CharacterData(): ViewModel() {
        var data: MutableLiveData<CharacterAPI> = MutableLiveData() //класс у которого можно отслеживать изменения с помощю обсервера

        suspend fun parseData(page: Int): CharacterAPI {    //запрос ретрофита
            val retrofit = Retrofit.Builder()
                .baseUrl("https://rickandmortyapi.com")
                .addConverterFactory(GsonConverterFactory.create()).build()

            val productAPI = retrofit.create(RickAndMortyApiService::class.java)// результат запроса ретрофита

            val characters = productAPI.getCharacters(page) // из запроса вытягиваем нужные нам данные
            return characters
        }

        val coroutine = CoroutineScope(Dispatchers.IO).async {  //АСИНХРОННЫЙ ЗАПРОС , а не блокед   io определяет на каком уровне вызывать эту корутину (уровень интерфейса)
            data.postValue(parseData(1)) }  // функция которая изменит значения переменной после выполнения
    }




}
