package com.example.agenda.inside

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
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
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class VerNota : AppCompatActivity() {
    private lateinit var DB : AgendaDB
    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ver_nota)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        DB= Room.databaseBuilder(application, AgendaDB::class.java, AgendaDB.DATABASE_NAME)
            .allowMainThreadQueries().build()

        val txtTituloNota= findViewById<EditText>(R.id.txtTituloNota2)
        val txtContenidoNota= findViewById<EditText>(R.id.txtContenidoNota2)
        val btnActualizarNota= findViewById<ImageView>(R.id.btnActualizarNota)
        val btnBackToCrearNota2= findViewById<ImageButton>(R.id.btnBackToCrearNota2)
        val btnBorrarNota= findViewById<Button>(R.id.btnBorrarNota)

        val materia= intent.getSerializableExtra("materia") as? Materia
        val nota= intent.getSerializableExtra("nota") as? Nota

        if (nota != null){
            txtTituloNota.setText(nota.titulo)
            txtContenidoNota.setText(nota.contenido)
        }

        btnActualizarNota.setOnClickListener {
            if (materia != null) {
                if (nota != null) {
                    actualizarNota(nota,materia, txtTituloNota, txtContenidoNota, DB)
                }
            }
        }
        btnBorrarNota.setOnClickListener {
            if (nota != null) {
                borrarNota(nota, DB)
            }
        }

        btnBackToCrearNota2.setOnClickListener {
            irAtras()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun obtenerFechaHoraActual(): String {
        val fechaHoraActual = LocalDateTime.now()
        val formato = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
        return fechaHoraActual.format(formato)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun actualizarNota(nota:Nota, materia: Materia, txtTituloNota: EditText, txtContenidoNota: EditText, db: AgendaDB) {
        val materiaId= materia.materiaId
        val tituloNota = txtTituloNota.text.toString()
        val contenidoNota = txtContenidoNota.text.toString()
        val fecha=obtenerFechaHoraActual()


        if (tituloNota.isNotEmpty() && contenidoNota.isNotEmpty()) {
            val nota: Nota= Nota( notaId = nota.notaId,materiaId = materiaId, titulo = tituloNota, contenido = contenidoNota, fecha = fecha)
            try {
                db.NotaDao.updateNota(nota)
                Toast.makeText(this, "Nota actualizada", Toast.LENGTH_SHORT).show()
                recreate()
            } catch (e: Exception) {
                Toast.makeText(this, "Error al actualizar la nota", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "complete la informacion", Toast.LENGTH_SHORT).show()
        }
    }

    fun borrarNota(nota: Nota, db: AgendaDB){
        val notaId= nota.notaId

        // Crear un diálogo de confirmación
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmar eliminación")
        builder.setMessage("¿Estás seguro de que deseas eliminar esta nota?")

        // Agregar botones al diálogo
        builder.setPositiveButton("Sí") { dialog, which ->
            try {
                db.NotaDao.deleteNotaById(notaId)
                Toast.makeText(this, "Nota Borrada", Toast.LENGTH_SHORT).show()
                irAtras()
            } catch (e: Exception) {
                Toast.makeText(this, "Problema al eliminar nota: $e", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("No") { dialog, which ->
        }

        builder.show()
    }


    fun irAtras() {
        val intent= Intent(this, MateriasMain::class.java)
        startActivity(intent)
        this.finish()
    }

}