package com.example.agenda.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import com.example.agenda.R
import com.example.agenda.configuracion.AgendaDB
import com.example.agenda.model.Usuario

class CrearCuenta : AppCompatActivity() {
    private lateinit var DB : AgendaDB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_crear_cuenta)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtUpCedula= findViewById<EditText>(R.id.txtUpCedula)
        val txtUpNombre= findViewById<EditText>(R.id.txtUpNombre)
        val txtUpCorreo= findViewById<EditText>(R.id.txtUpCorreo)
        val txtUpContraseña= findViewById<EditText>(R.id.txtUpContraseña)
        val btnCrearCuenta= findViewById<Button>(R.id.btnCrearCuenta)

        DB= Room.databaseBuilder(application, AgendaDB::class.java, AgendaDB.DATABASE_NAME)
            .allowMainThreadQueries().build()

        btnCrearCuenta.setOnClickListener {
            validarCamposNoVacios(txtUpCedula, txtUpNombre,txtUpCorreo,txtUpContraseña,DB)
        }


    }

    fun validarCamposNoVacios(txtId: EditText, txtNombre: EditText, txtCorreo: EditText, txtContraseña: EditText, db: AgendaDB) {
        val id = txtId.text.toString().trim()
        val nombre = txtNombre.text.toString().trim()
        val correo = txtCorreo.text.toString().trim()
        val contraseña = txtContraseña.text.toString().trim()

        if (id.isNotEmpty() && nombre.isNotEmpty() && correo.isNotEmpty() && contraseña.isNotEmpty()) {
            val usuario: Usuario= Usuario(id, nombre,correo,contraseña)
            try {
                db.UsuarioDao.insertUsuario(usuario)
                Toast.makeText(this, "Usuario creado", Toast.LENGTH_SHORT).show()
                irALogin()
            } catch (e: Exception){
                Toast.makeText(this, "Error al guardar el usuario", Toast.LENGTH_SHORT).show()
            }
        }else {
            Toast.makeText(this, "Llene todos los campos", Toast.LENGTH_SHORT).show()
        }
    }

    fun irALogin(){
        val intent= Intent(this, Login::class.java)
        startActivity(intent)
        this.finish()
    }
}