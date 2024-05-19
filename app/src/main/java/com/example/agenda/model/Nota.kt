package com.example.agenda.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "notas",
    foreignKeys = [ForeignKey(entity = Materia::class,
        parentColumns = ["materiaId"],
        childColumns = ["materiaId"],
        onDelete = ForeignKey.CASCADE)]
)
data class Nota(
    @PrimaryKey(autoGenerate = true) val notaId: Int=0,
    val titulo: String,
    val contenido: String,
    val fecha: String,
    val materiaId: Int
):Serializable
