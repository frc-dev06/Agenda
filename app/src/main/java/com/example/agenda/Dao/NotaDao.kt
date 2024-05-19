package com.example.agenda.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.agenda.model.Nota

@Dao
interface NotaDao {
    @Query("SELECT * FROM notas")
    fun getAllNotas(): List<Nota>

    @Query("SELECT * FROM notas WHERE notaId = :notaId")
    fun getNotaById(notaId: Int): Nota?

    @Query("SELECT * FROM notas WHERE materiaId = :materiaId")
    fun getNotasByMateriaId(materiaId: Int): List<Nota>

    @Insert
    fun insertNota(nota: Nota)

    @Update
    fun updateNota(nota: Nota)

    @Query("DELETE FROM notas WHERE notaId = :idNota")
    fun deleteNotaById(idNota: Int)
}