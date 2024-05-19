package com.example.agenda.login

import android.content.Context
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
import com.example.agenda.inside.Inicio
import com.example.agenda.model.Usuario

class Login : AppCompatActivity() {
    private lateinit var DB : AgendaDB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnLoginIniciar= findViewById<Button>(R.id.btnLoginIniciar)
        val btnLoginCrear= findViewById<Button>(R.id.btnLoginCrear)

        val txtLoginId= findViewById<EditText>(R.id.txtLoginId)
        val txtLoginPass= findViewById<EditText>(R.id.txtLoginPass)

        DB= Room.databaseBuilder(application, AgendaDB::class.java, AgendaDB.DATABASE_NAME)
            .allowMainThreadQueries().build()


        btnLoginCrear.setOnClickListener {
            irACrearCuenta()
        }

        btnLoginIniciar.setOnClickListener {
            validarCamposNoVacios(txtLoginId, txtLoginPass, DB)
        }
    }

    fun irACrearCuenta(){
        val intent= Intent(this, CrearCuenta::class.java)
        startActivity(intent)
       // this.finish()
    }
    fun irAInicio(usuario: Usuario){
        val sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.putString("nombre", usuario.nombre)
        editor.putString("cedula", usuario.cedulaUsuario)
        editor.apply()

        runOnUiThread {
            val intent= Intent(this, Inicio::class.java)
            startActivity(intent)
            this.finish()
        }

    }

    fun validarCamposNoVacios(txtId: EditText, txtPass: EditText, db: AgendaDB) {
        val id = txtId.text.toString()
        val pass = txtPass.text.toString()

        if (!id.isEmpty() && !pass.isEmpty()) {
            try {
                val usuario=db.UsuarioDao.getUsuarioByCedula(id)
                if (usuario?.contraseña == pass){
                    irAInicio(usuario)
                }else{
                    Toast.makeText(this, "Revisa tus credenciales", Toast.LENGTH_SHORT).show()
                }

            }catch (e:Exception){
                Toast.makeText(this, "Usuario no existe", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(this, "Llene los campos", Toast.LENGTH_SHORT).show()
        }
    }
}