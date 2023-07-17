package com.example.maps

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DatosHoteles : AppCompatActivity() {

    private  lateinit var  recyclerView: RecyclerView
    private lateinit var  hotelAdapter: HotelAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.datos_hoteles)

        val hotels = listOf(
            Hotel("HOTEL 5 DE MAYO", "Calle", "5 de mayo", "Colonia", "Misantla Centro"),
            Hotel("HOTEL CLAUDIA ISABEL", "Calle", "Santos Degollado", "Colonia", "Misantla Centro"),
            Hotel("HOTEL DELFOS", "Calle", "Francisco I. Madero", "Colonia", "Misantla Centro"),
            Hotel("HOTEL DON PABLO", "Calle", "5 de mayo", "Colonia", "Misantla Centro"),
            Hotel("HOTEL EL DORADO AMANECER", "Avenida", "Manuel Ávila Camacho", "Colonia", "20 de Noviembre"),
            Hotel("HOTEL FIDELIDAD", "Calle", "LAS CRUCES", "Colonia", "Misantla Centro"),
            Hotel("HOTEL GILBÓN", "Calle", "Santos Degollado", "Colonia", "Misantla Centro"),
            Hotel("HOTEL IMPERIAL", "Calle", "Manuel Acuña", "Colonia", "Misantla Centro"),
            Hotel("HOTEL LEON", "Calle", "Santos Degollado", "Colonia", "Centro"),
            Hotel("HOTEL MISANTLA", "Calle", "Carlos Salinas De Gortari", "Colonia", "Aviacion"),
            Hotel("HOTEL POSADA DEL ÁNGEL", "Calle", "Miguel Hidalgo", "Colonia", "Misantla Centro"),
            Hotel("HOTEL SOL", "Calle", "Jose Morelos Y Pavon", "Colonia", "Centro"),
            Hotel("AUTO HOTEL CIELO", "Calle", "Francisco Gonzalez Bocanegra", "Fraccionamiento", "Los arcangeles"),
            Hotel("AUTO HOTEL DELICIAS","Calle", "Jose Maria Pino Suarez", "Colonia", "Misantla Centro"),
            Hotel("AUTO HOTEL ROMANCES", "Calle", "Jose Maria Pino Suarez", "Colonia", "Misantla Centro"),
            Hotel("AUTO HOTEL TENTACIONES", "Carretera", "Carretera a Tenochtitlan", "Colonia", "Pedregal 2"),
            Hotel("POSADA HOTEL CLAUDIA ISABEL", "Calle", "Santos Degollado", "Colonia", "Misantla Centro")
        )

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        hotelAdapter = HotelAdapter(hotels)
        recyclerView.adapter = hotelAdapter
    }
}