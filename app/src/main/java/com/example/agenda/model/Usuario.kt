package com.example.agenda.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey val cedulaUsuario: String,
    val nombre: String,
    val correo: String,
    val contraseña:String
):Serializable
