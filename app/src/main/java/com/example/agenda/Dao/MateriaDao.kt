package com.example.agenda.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.agenda.model.Materia
import java.io.Serializable

@Dao
interface MateriaDao {
    @Query("SELECT * FROM materias")
    fun getAllMaterias(): List<Materia>

    @Query("SELECT * FROM materias WHERE materiaId = :materiaId")
    fun getMateriaById(materiaId: Int): Materia?

    @Query("SELECT * FROM materias WHERE cedulaUsuario = :cedula")
    fun getMateriasByCedula(cedula: String?): List<Materia>

    @Insert
    fun insertMateria(materia: Materia)

    @Update
    fun updateMateria(materia: Materia)

    @Query("DELETE FROM materias WHERE materiaId = :materiaId")
    fun deleteMateria(materiaId: Int)

}