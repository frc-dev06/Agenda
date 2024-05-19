package com.example.agenda.inside

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import com.example.agenda.R
import com.example.agenda.adapter.MateriasAdapter
import com.example.agenda.configuracion.AgendaDB
import com.example.agenda.model.Materia
import java.io.Serializable

class MateriasMain : AppCompatActivity() {
    private lateinit var DB : AgendaDB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_materias_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val nombreUsuario = sharedPreferences.getString("nombre", "")
        val cedulaUsuario = sharedPreferences.getString("cedula", "")

        val btnBackToMaterias= findViewById<ImageButton>(R.id.btnBackToMaterias)

        val btnCrearMateria= findViewById<ImageButton>(R.id.btnCrearMateria)
        val listaMaterias= findViewById<ListView>(R.id.listaNotas)

        DB= Room.databaseBuilder(application, AgendaDB::class.java, AgendaDB.DATABASE_NAME)
            .allowMainThreadQueries().build()

        var listadoMaterias: List<Materia> = emptyList()

            listadoMaterias= DB.MateriaDao.getMateriasByCedula(cedulaUsuario)
            val adapter= MateriasAdapter(this@MateriasMain, listadoMaterias)
            listaMaterias.adapter=adapter

        listaMaterias.setOnItemClickListener { parent, view, position, id ->
            val intent= Intent(this@MateriasMain, NotasMain::class.java)
            if (position<listadoMaterias.size){
                intent.putExtra("materia", listadoMaterias[position])
                startActivity(intent)
            }
        }


        btnCrearMateria.setOnClickListener {
            irACrearMateria()
        }
        btnBackToMaterias.setOnClickListener {
            irAtras()
        }



    }

    fun irACrearMateria() {
        val intent= Intent(this, CrearMateria::class.java)
        startActivity(intent)
        this.finish()
    }
    fun irAtras() {
        val intent= Intent(this, Inicio::class.java)
        startActivity(intent)
        this.finish()
    }

}