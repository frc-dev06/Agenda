package com.example.agenda.inside

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import com.example.agenda.R
import com.example.agenda.adapter.NotasAdapter
import com.example.agenda.configuracion.AgendaDB
import com.example.agenda.model.Materia
import com.example.agenda.model.Nota

class NotasMain : AppCompatActivity() {
    private lateinit var DB : AgendaDB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_notas_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val materia= intent.getSerializableExtra("materia") as? Materia

        val listaNotas= findViewById<ListView>(R.id.listaNotas)
        val btnBackToNotas= findViewById<ImageButton>(R.id.btnBackToNotas)
        val btnCrearNota= findViewById<ImageButton>(R.id.btnCrearNota)
        val btnBorrarMateria= findViewById<Button>(R.id.btnBorrarMateria)

        DB= Room.databaseBuilder(application, AgendaDB::class.java, AgendaDB.DATABASE_NAME)
            .allowMainThreadQueries().build()

        var listadoNotas: List<Nota> = emptyList()
        if (materia != null) {
            listadoNotas= DB.NotaDao.getNotasByMateriaId(materia.materiaId)
        }
        val adapter= NotasAdapter(this@NotasMain, listadoNotas)
        listaNotas.adapter= adapter


        listaNotas.setOnItemClickListener { parent, view, position, id ->
            val intent= Intent(this@NotasMain, VerNota::class.java)

            if (position<listadoNotas.size){
                intent.putExtra("materia", materia)
                intent.putExtra("nota", listadoNotas[position])
                startActivity(intent)
            }
        }


        btnBackToNotas.setOnClickListener {
            irAtras()
        }
        btnCrearNota.setOnClickListener {
            if (materia != null) {
                irANotas(materia)
            }
        }
        btnBorrarMateria.setOnClickListener {
            if (materia != null) {
                borrarMateria(materia, DB)
            }
        }

    }
    fun irAtras() {
        val intent= Intent(this, MateriasMain::class.java)
        startActivity(intent)
        this.finish()
    }
    fun irANotas(materia: Materia) {
        val intent= Intent(this, CrearNota::class.java)
        intent.putExtra("materia", materia)
        startActivity(intent)
        this.finish()
    }

    fun borrarMateria(materia: Materia, db: AgendaDB){
        val materiaId= materia.materiaId

        // Crear un diálogo de confirmación
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmar eliminación")
        builder.setMessage("¿Estás seguro de que deseas eliminar esta Materia?")

        // Agregar botones al diálogo
        builder.setPositiveButton("Sí") { dialog, which ->
            try {
                db.MateriaDao.deleteMateria(materiaId)
                Toast.makeText(this, "Materia Borrada", Toast.LENGTH_SHORT).show()
                irAtras()
            } catch (e: Exception) {
                Toast.makeText(this, "Problema al eliminar materia: $e", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("No") { dialog, which ->
        }

        builder.show()
    }

}