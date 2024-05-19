package com.example.agenda.inside

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import com.example.agenda.R
import com.example.agenda.configuracion.AgendaDB
import com.example.agenda.model.Materia
import java.io.Serializable

class CrearMateria : AppCompatActivity() {

    private lateinit var DB : AgendaDB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_crear_materia)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val nombreUsuario = sharedPreferences.getString("nombre", "")
        val cedulaUsuario = sharedPreferences.getString("cedula", "")

        val txtNombreMateria= findViewById<EditText>(R.id.txtNombreMateria)
        val txtNombreDocente= findViewById<EditText>(R.id.txtNombreDocente)
        val btnRegistrarMateria= findViewById<Button>(R.id.btnRegistrarMateria)
        val btnBackToCrearMat= findViewById<ImageButton>(R.id.btnBackToCrearMat)

        DB= Room.databaseBuilder(application, AgendaDB::class.java, AgendaDB.DATABASE_NAME)
            .allowMainThreadQueries().build()

        btnRegistrarMateria.setOnClickListener {

            if (cedulaUsuario != null) {
                validarCamposNoVacios(cedulaUsuario,txtNombreMateria, txtNombreDocente, DB)
            }

        }

        btnBackToCrearMat.setOnClickListener {
            irAtras()
        }


    }

    fun validarCamposNoVacios(cedula: String, txtNombreMateria: EditText, txtNombreDocente: EditText, db: AgendaDB) {
        val cedulaUsuario= cedula
        val nombreMateria = txtNombreMateria.text.toString()
        val nombreDocente = txtNombreDocente.text.toString()

        if (nombreMateria.isNotEmpty() && nombreDocente.isNotEmpty()) {
            val materia: Materia= Materia(cedulaUsuario = cedulaUsuario, nombreMateria = nombreMateria, nombreDocente = nombreDocente)
            try {
                db.MateriaDao.insertMateria(materia)
                Toast.makeText(this, "Registrado", Toast.LENGTH_SHORT).show()
                irAtras()
            } catch (e: Exception) {
                Toast.makeText(this, "Error al guardar la materia", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Llene todos los campos", Toast.LENGTH_SHORT).show()
        }
    }

    fun irAtras() {
        val intent= Intent(this, MateriasMain::class.java)
        startActivity(intent)
        this.finish()
    }
}