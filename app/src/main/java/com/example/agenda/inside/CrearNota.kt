package com.example.agenda.inside

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import com.example.agenda.R
import com.example.agenda.configuracion.AgendaDB
import com.example.agenda.model.Materia
import com.example.agenda.model.Nota
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CrearNota : AppCompatActivity() {
    private lateinit var DB : AgendaDB
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_crear_nota)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val materia= intent.getSerializableExtra("materia") as? Materia

        val txtTituloNota= findViewById<EditText>(R.id.txtTituloNota)
        val txtContenidoNota= findViewById<EditText>(R.id.txtContenidoNota)
        val btnRegistrarNota= findViewById<Button>(R.id.btnRegistrarNota)
        val btnBackToCrearNota= findViewById<ImageButton>(R.id.btnBackToCrearNota)




        DB= Room.databaseBuilder(application, AgendaDB::class.java, AgendaDB.DATABASE_NAME)
            .allowMainThreadQueries().build()

        btnBackToCrearNota.setOnClickListener {
            irANotas()
        }
        btnRegistrarNota.setOnClickListener {
            if (materia != null) {
                validarCamposNoVacios(materia, txtTituloNota, txtContenidoNota, DB)
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun obtenerFechaHoraActual(): String {
        val fechaHoraActual = LocalDateTime.now()
        val formato = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
        return fechaHoraActual.format(formato)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun validarCamposNoVacios(materia: Materia, txtTituloNota: EditText, txtContenidoNota: EditText, db: AgendaDB) {
        val materiaId= materia.materiaId
        val tituloNota = txtTituloNota.text.toString()
        val contenidoNota = txtContenidoNota.text.toString()
        val fecha=obtenerFechaHoraActual()


        if (tituloNota.isNotEmpty() && contenidoNota.isNotEmpty()) {
            val nota: Nota= Nota(materiaId = materiaId, titulo = tituloNota, contenido = contenidoNota, fecha = fecha)
            try {
                db.NotaDao.insertNota(nota)
                Toast.makeText(this, "Nota registrada", Toast.LENGTH_SHORT).show()
                this.finish()
            } catch (e: Exception) {
                Toast.makeText(this, "Error al guardar la nota", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Llene todos los campos", Toast.LENGTH_SHORT).show()
        }
    }


    fun irANotas() {
        val intent= Intent(this, MateriasMain::class.java)
        startActivity(intent)
        this.finish()
    }
}