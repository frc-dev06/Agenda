package com.example.agenda.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.agenda.model.Usuario
import java.io.Serializable


@Dao
interface UsuarioDao {
    @Query("SELECT * FROM usuarios")
    fun getAllUsuarios(): List<Usuario>

    @Query("SELECT * FROM usuarios WHERE cedulaUsuario = :cedula")
    fun getUsuarioByCedula(cedula: String ): Usuario?

    @Insert
    fun insertUsuario(usuario: Usuario)

    @Update
    fun updateUsuario(usuario: Usuario)

    @Delete
    fun deleteUsuario(usuario: Usuario)


}