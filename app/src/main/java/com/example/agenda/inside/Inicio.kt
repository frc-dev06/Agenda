package com.example.agenda.inside

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.agenda.R
import com.example.agenda.model.Usuario

class Inicio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inicio)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val nombreUsuario = sharedPreferences.getString("nombre", "")
        val cedulaUsuario = sharedPreferences.getString("cedula", "")

        val txtHola= findViewById<TextView>(R.id.txtHola)
        val btnNotas= findViewById<Button>(R.id.btnNotas)
        val btnCalcularNotas= findViewById<Button>(R.id.btnCalcularNotas)


        txtHola.setText("Hola ${nombreUsuario}  !!")


        btnNotas.setOnClickListener {
            if (cedulaUsuario != null) {
                irANotas()
            }
        }

        btnCalcularNotas.setOnClickListener {
            irACalcularNotas()
        }


    }

    fun irANotas() {
        val intent= Intent(this, MateriasMain::class.java)
        startActivity(intent)
        this.finish()
    }

    fun irACalcularNotas() {
        val intent= Intent(this, CalcularPromedio::class.java)
        startActivity(intent)
        this.finish()
    }
}